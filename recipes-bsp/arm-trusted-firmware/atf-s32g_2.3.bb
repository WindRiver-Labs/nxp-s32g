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
BRANCH ?= "release/bsp29.0-2.3"
SRC_URI = "${URL};branch=${BRANCH}"
SRCREV ?= "87dbb869d05399cef3ec596599c8080a090ba17c"
SRC_URI[sha256sum] = "4ded53541fb0ac8840f5a1e23ecfa546a1ca15246a73a15707ff8c76f47f7729"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:" 
SRC_URI += " \
    file://0001-Fix-fiptool-build-error.patch \
    file://bsp30/0001-s32-Enable-workaround-for-ARM-errata-1530924.patch \
    file://bsp30/0002-s32g-minimal-initialization-during-resume-path.patch \
    file://bsp30/0003-s32g-Move-PMIC-WDG-programming-after-initializations.patch \
    file://bsp30/0004-s32g-Prevent-reset-escalation.patch \
    file://bsp30/0005-s32g274a-Add-dts-fixup-as-part-of-LPDDR4-ERR050543.patch \
    file://bsp30/0006-s32g-Report-min-and-max-frequencies-for-clocks-over-.patch \
    file://bsp30/0007-s32g-Add-minimal-support-for-DFS.patch \
    file://bsp30/0008-s32g-Add-OCOTP-driver-for-S32GEN1-platforms.patch \
    file://bsp30/0009-dts-Add-OCOTP-nodes-for-S32G.patch \
    file://bsp30/0010-s32g-Apply-VDD_CORE-adjustment.patch \
    file://bsp30/0011-s32g-reset-GPR-and-A53-generic-timers-in-early-boot-.patch \
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
