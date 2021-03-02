DESCRIPTION = "NXP HSE PKCS#11 Module"
PROVIDES += "pkcs11-hse"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = " \
    file://README.md;md5=9528eb504d89ee849a198bbfa981ea5b \
"

DEPENDS = "openssl libp11"

SRC_URI = "https://bitbucket.sw.nxp.com/projects/ALBW/repos/pkcs11-hse/pkcs11-hse.tar.gz"

SRCREV = "d951d55997b3f8db84901bc7058bec0b7a07560a"
SRC_URI[sha256sum] = "7d8056117894df028f3bfa13b9b0842a794c74013f7ea1cabb165db9c1558b32"

SRC_URI += "file://0001-pkcs-example-modify-the-example-codes-to-workable.patch \
	file://0001-Add-license-file.patch \
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
