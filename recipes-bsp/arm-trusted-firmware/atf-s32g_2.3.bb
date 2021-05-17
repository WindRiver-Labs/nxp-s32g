# Copyright 2019-2020 NXP

DESCRIPTION = "ARM Trusted Firmware"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://license.rst;md5=1dd070c98a281d18d9eefd938729b031"

DEPENDS += "dtc-native xxd-native"
DEPENDS += "openssl-native"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

# ATF repository
# URL ?= "git://source.codeaurora.org/external/autobsps32/arm-trusted-firmware.git;protocol=https"
URL ?= "git://source.codeaurora.org/external/autobsps32/arm-trusted-firmware;protocol=https"
BRANCH ?= "release/bsp28.0-2.3"
SRC_URI = "${URL};branch=${BRANCH}"
SRCREV ?= "b0087f0173a47c942696c01dd1ade96aa8d42e6a"
SRC_URI[sha256sum] = "4ded53541fb0ac8840f5a1e23ecfa546a1ca15246a73a15707ff8c76f47f7729"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:" 
SRC_URI += " \
    file://0001-Fix-fiptool-build-error.patch \
    file://0001-s32g-Replace-DMA-copy-using-core-copy-operations.patch \
    file://0002-s32g-QSPI-boot-support.patch \
    file://0003-s32g274-clk-scmi-Use-S32G-instead-of-S32G274.patch \
    file://0004-clk-s32gen1-Correct-GMAC-SGMII-clock.patch \
    file://0005-s32g-clk-Check-clock-state-before-returning-errors.patch \
    file://0006-s32g-build-Specify-MKIMAGE-binary-as-build-parameter.patch \
    file://0007-dt-bindings-s32g-Add-SCMI-reset-IDs.patch \
    file://0008-s32g-Add-SCMI-reset-domain-implementation.patch \
    file://0009-s32g-clk-Align-with-U-Boot-names.patch \
    file://0010-s32g-clk-Align-with-U-Boot-clock-implementation.patch \
    file://0011-clk-s32gen1-Update-SDHC-clock-frequency-to-400MHz.patch \
    file://0012-tf-a-fix-boot-flow-from-eMMC.patch \
    file://0013-s32gen1-uart-Suppress-printing-to-shared-console.patch \
    file://0014-Add-partitions-to-SCMI-Reset-Domains.patch \
"

PLATFORM_nxp-s32g2xx = "s32g"
BUILD_TYPE = "release"

ATF_BINARIES = "${B}/${PLATFORM}/${BUILD_TYPE}"


EXTRA_OEMAKE += " \
                CROSS_COMPILE=${TARGET_PREFIX} \
                ARCH=${TARGET_ARCH} \
                BUILD_BASE=${B} \
                PLAT=${PLATFORM} \
                "

# FIXME: Allow linking of 'tools' binaries with native libraries
#        used for generating the boot logo and other tools used
#        during the build process.
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CPPFLAGS}" \
                 HOSTLD="${BUILD_LD} -L${STAGING_BASE_LIBDIR_NATIVE} \
                 -Wl,-rpath,${STAGING_LIBDIR_NATIVE} \
                 -Wl,-rpath,${STAGING_BASE_LIBDIR_NATIVE}" \
                 LIBPATH="${STAGING_LIBDIR_NATIVE}" \
                 HOSTSTRIP=true'

do_compile() {
	unset LDFLAGS
	unset CFLAGS
	unset CPPFLAGS

	oe_runmake -C ${S} BL33="${DEPLOY_DIR_IMAGE}/u-boot.bin" all
}

do_deploy() {
	install -d ${DEPLOY_DIR_IMAGE}
	cp -v ${ATF_BINARIES}/fip.s32 ${DEPLOY_DIR_IMAGE}/${ATF_IMAGE_FILE}
}

addtask deploy after do_compile

do_compile[depends] = "virtual/bootloader:do_install"

COMPATIBLE_MACHINE = "nxp-s32g2xx"
ALLOW_EMPTY_${PN} = "1"
