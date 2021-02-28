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
URL ?= "https://source.codeaurora.org/external/autobsps32/arm-trusted-firmware_2.3.tar.gz"
BRANCH ?= "release/bsp27.0-2.3"
SRC_URI = "${URL};branch=${BRANCH}"
SRCREV ?= "9cbe18294976c5192fec602aaaafa284a233c734"
SRC_URI[sha256sum] = "4ded53541fb0ac8840f5a1e23ecfa546a1ca15246a73a15707ff8c76f47f7729"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:" 
SRC_URI += "file://0001-Fix-fiptool-build-error.patch \
	file://0001-dt-bindings-s32g274a-Add-LLCE-clocks-indexes.patch \
	file://0002-drivers-clk-s32g274a-Add-LLCE-clocks.patch \
	file://0003-s32-gen1-Set-SDHC_CLK-to-400MHz.patch \
	file://0004-plat-s32g-Place-BL33-in-DDR.patch \
	file://0005-s32g-Refactor-UART-driver.patch \
	file://0006-s32g-Define-platform-crash-callbacks.patch \
	file://0007-s32g274a-rev1-clock-Use-666.-6-MHZ-for-DDR_CLK.patch \
	file://0008-s32g274a-Upgrade-DDR-firmware.patch \
	file://0009-s32g-Adapt-DDR-retention-mode-glue-code-for-firmware.patch \
	file://0010-s32g274a-rev2-clock-Use-800-MHZ-for-DDR_CLK.patch \
	file://0011-s32g274a-Enable-3200-MT-s-on-DDR-for-rev-2.patch \
	file://0012-dt-bindings-s32g274a-Add-WKPU-boot-select.patch \
	file://0013-dts-s32g274a-Move-I2C-and-WKPU-to-S32GEN1.patch \
	file://0014-dts-s32gen1-Select-long-warm-boot.patch \
	file://0015-s32g274a-Add-support-for-long-boot.patch \
	file://0016-s32g-clk-Add-SAR_ADC-clock.patch \
	file://0017-s32g-Use-S32G_DRAM_INLINE_ECC-make-flag-to-enable-EC.patch \
	file://0018-s32g-ddr-Add-a-way-to-skip-memory-initialization.patch \
	file://0019-s32g-ddr-ECC-management-for-STR-use-case.patch \
	file://0020-dt-bindigns-s32g-Define-QSPI-clock-frequencies.patch \
	file://0021-dts-s32g-Enable-QSPI-clock.patch \
	file://0022-s32g-Set-QSPI-clock-based-on-SoC-revision.patch \
	file://0001-s32g-mmc-Fix-while-loops-and-remove-unneeded-udelay.patch \
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
