# Copyright 2019-2020 NXP

DESCRIPTION = "ARM Trusted Firmware"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://license.rst;md5=1dd070c98a281d18d9eefd938729b031"

DEPENDS += "dtc-native xxd-native"
DEPENDS += "openssl-native"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

# ATF repository
URL ?= "git://source.codeaurora.org/external/autobsps32/arm-trusted-firmware;protocol=https"
BRANCH ?= "release/bsp31.0-2.5"
SRC_URI = "${URL};branch=${BRANCH}"
SRCREV ?= "daa402f07e4d88e5c887a64a709b2f9839038c14"
SRC_URI[sha256sum] = "15d263b62089b46375effede12a1917cd7b267b93dd97c68fd5ddbd1dddede07"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:" 
SRC_URI += " \
    file://0001-Fix-fiptool-build-error.patch \
    file://bsp32/rc1/0001-s32g3-Rename-S32G398-to-S32G399.patch \
    file://bsp32/rc1/0002-s32g-fix-accessing-s32g_policies-array-outside-array.patch \
    file://bsp32/rc1/0003-s32g-Skip-over-non-sgi-interrupts-during-cpu-resume.patch \
    file://bsp32/rc1/0004-bindings-clock-Use-one-clock-ID-for-GMAC-TS-clock.patch \
    file://bsp32/rc1/0005-s32g-scmi-Use-one-clock-ID-for-GMAC-TS-clock.patch \
    file://bsp32/rc1/0006-s32g-Correct-SRAM-initialization-algorithm.patch \
    file://bsp32/rc1/0007-s32g-clk-Remove-unused-function.patch \
    file://bsp32/rc1/0008-plat-s32g-Reduce-fip-size-to-account-for-M7-bootload.patch \
    file://bsp32/rc1/0009-s32g-Add-AHB-buffer-to-MMU-tables-when-needed-only.patch \
    file://bsp32/rc2/0001-s32gen1-sramc-Adjust-SRAMC-range-to-delete-only-the-.patch \
    file://bsp32/rc2/0002-s32g-sramc-Don-t-substract-SRAM-base-from-an-SRAM-of.patch \
    file://bsp32/rc2/0003-drivers-s32g-pmic-Fix-uninitialized-usage-warning.patch \
    file://bsp32/rc2/0004-drivers-mmc-Fix-uninitialized-variable-warning.patch \
    file://bsp32/rc2/0005-plat-s32g-Use-Os-optimization-level-on-release-build.patch \
    file://bsp32/rc2/0006-wkpu-remove-GPIO166-external-wake-up-source.patch \
"

PLATFORM_nxp-s32g2xx = "s32g2"
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
