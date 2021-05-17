DESCRIPTION = "NXP HSE PKCS#11 Module"
PROVIDES += "pkcs11-hse"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = " \
    file://README.md;md5=b451d36d865e4242aa2b944fb0370269 \
"

DEPENDS = "openssl libp11"

SRC_URI = "https://bitbucket.sw.nxp.com/projects/ALBW/repos/pkcs11-hse/pkcs11-hse.tar.gz"

SRCREV = "551115b2a261073667744db13510393ee76c89c9"
SRC_URI[sha256sum] = "20de6b70bd8e8eaa225954480e2692c281b23d67a5f3986d5834b6c1242e6781"

SRC_URI += " \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/pkcs11-hse"

COMPATIBLE_MACHINE_nxp-s32g2xx = "nxp-s32g2xx"

do_compile() {
    sed -i 's/$(CROSS_COMPILE)gcc /$(CC) /' Makefile examples/Makefile
    oe_runmake HSE_FWDIR=${S}/hes-fw  CFLAGS="${CFLAGS} -shared -fPIC -Wall -fno-builtin"
    oe_runmake -C examples LIBS="-L${STAGING_LIBDIR}/" INCLUDE="-I${STAGING_INCDIR}" LDFLAGS="${LDFLAGS} -lcrypto -lp11"
}

do_install() {
    install -d ${D}${libdir}
    install -m 0644 ${S}/pkcs11-hse.so ${D}${libdir}/libpkcs11-hse.so.1.0
    ln -s libpkcs11-hse.so.1.0 ${D}${libdir}/libpkcs11-hse.so
    install -d ${D}${includedir}
    install -m 0644 ${S}/src/*.h ${D}${includedir}

    install -d ${D}${bindir}
    install -m 0755 ${S}/examples/pkcs-keyop ${D}${bindir}
}

PACKAGES =+ "${PN}-examples "
FILES_${PN}-examples += "${bindir}"
