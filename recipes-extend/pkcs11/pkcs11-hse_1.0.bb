DESCRIPTION = "NXP HSE PKCS#11 Module"
PROVIDES += "pkcs11-hse"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = " \
    file://README.md;md5=b451d36d865e4242aa2b944fb0370269 \
"

DEPENDS = "openssl libp11"

SRC_URI = "https://bitbucket.sw.nxp.com/projects/ALBW/repos/pkcs11-hse/pkcs11-hse.tar.gz"

SRCREV = "2ab4eb6b73f1f1296576e45f333eb0911a2cae00"
SRC_URI[sha256sum] = "1b8ebaafa247d8cc873d84c3fab70f1e3d05cdb20140910da2d7d6229fdc6de5"

SRC_URI += " \
    file://0001-pkcs11-hse-Makefile-using-internal-compile-variables.patch \
    file://bsp32/rc3/0001-pkcs-fix-unaligned-memcpy-for-ec-keys.patch \
    file://bsp32/rc3/0002-pkcs-add-error-checks-on-calls-to-getattr_len.patch \
    file://bsp32/rc3/0003-pkcs-fix-goto-labels-for-consistency.patch \
    file://bsp32/rc4/0001-examples-add-low-level-libhse-example.patch \
"

PATCHTOOL = "git"
PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/pkcs11-hse"

EXTRA_OEMAKE += " \
	CROSS_COMPILE=${TARGET_PREFIX} \
"

do_compile() {
    oe_runmake HSE_FWDIR=${S}/hse-fw  CFLAGS="${CFLAGS} -shared -fPIC -Wall -fno-builtin"
    oe_runmake -C examples HSE_FWDIR=${S}/hse-fw LIBS="-L${STAGING_LIBDIR}/" INCLUDE="-I${STAGING_INCDIR}" LDFLAGS="${LDFLAGS} -lcrypto -lp11" "${HOST_CC_ARCH}${TOOLCHAIN_OPTIONS}"
}

do_install() {
    install -d ${D}${libdir}

    install -m 0755 ${S}/libpkcs-hse.so ${D}${libdir}/libpkcs-hse.so.1.0
    ln -s libpkcs-hse.so.1.0 ${D}${libdir}/libpkcs-hse.so
    install -m 0755 ${S}/libhse.so.1.0 ${D}${libdir}/libhse.so.1.0
    ln -s libhse.so.1.0 ${D}${libdir}/libhse.so.1

    install -d ${D}${includedir}
    install -m 0644 ${S}/libhse/*.h ${D}${includedir}
    install -m 0644 ${S}/libpkcs/*.h ${D}${includedir}

    install -d ${D}${bindir}
    install -m 0755 ${S}/examples/pkcs-keyop ${D}${bindir}
}

PACKAGES =+ "${PN}-examples "
FILES_${PN}-examples += "${bindir}"
COMPATIBLE_MACHINE_nxp-s32g2xx = "nxp-s32g2xx"
