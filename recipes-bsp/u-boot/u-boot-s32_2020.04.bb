require recipes-bsp/u-boot/u-boot.inc

DESCRIPTION = "U-boot provided by NXP with focus on S32 chipsets"
PROVIDES += "u-boot"

LICENSE = "GPLv2 & BSD-3-Clause & BSD-2-Clause & LGPL-2.0 & LGPL-2.1"
LIC_FILES_CHKSUM = " \
    file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://Licenses/bsd-2-clause.txt;md5=6a31f076f5773aabd8ff86191ad6fdd5 \
    file://Licenses/bsd-3-clause.txt;md5=4a1190eac56a9db675d58ebe86eaf50c \
    file://Licenses/lgpl-2.0.txt;md5=5f30f0716dfdd0d91eb439ebec522ec2 \
    file://Licenses/lgpl-2.1.txt;md5=4fbd65380cdd255951079008b364516c \
"

INHIBIT_DEFAULT_DEPS = "1"
DEPENDS_append = " libgcc virtual/${TARGET_PREFIX}gcc python3 dtc-native bison-native"

inherit nxp-u-boot-localversion

SRC_URI:prepend = "git://source.codeaurora.org/external/autobsps32/u-boot;protocol=https;branch=release/bsp32.0-2020.04"

SRC_URI += " \
    file://0001-Make-s32g274ardb2-and-s32g2xxaevb-support-ostree.patch \
    file://0001-scripts-mailmapper-python2-python3.patch \
    file://0001-Makefile-add-.cfgout-file-dependency-to-fix-atf-buil.patch \
    file://0001-tools-s32gen1_secboot-replace-u-boot.s32-with-u-boot.patch \
"

SRCREV = "7cc85e188554fb38b6bd39a98b6149b033ebd53e"
SRC_URI[sha256sum] = "4e80caf195787c76639675712492230d090fe2eb435fd44491d653463485e30c"

SCMVERSION = "y"
LOCALVERSION = ""
PACKAGE_ARCH = "${MACHINE_ARCH}"
UBOOT_INITIAL_ENV = ""

USRC ?= ""
S = '${@oe.utils.conditional("USRC", "", "${WORKDIR}/git", "${USRC}", d)}'


# For now, only rdb2 boards support ATF, this function will be fixed when new ATF supported boards added.
do_install_append() {

    unset i j
    install -d ${DEPLOY_DIR_IMAGE}
    for config in ${UBOOT_MACHINE}; do
        i=$(expr $i + 1);
        for type in ${UBOOT_CONFIG}; do
            j=$(expr $j + 1)
            if  [ $j -eq $i ]; then
                install -d ${DEPLOY_DIR_IMAGE}/${type}/tools
                cp ${B}/${config}/${UBOOT_BINARY} ${DEPLOY_DIR_IMAGE}/${type}/${UBOOT_BINARY}
                cp ${B}/${config}/tools/mkimage ${DEPLOY_DIR_IMAGE}/${type}/tools/mkimage
                cp ${B}/${config}/${UBOOT_CFGOUT} ${DEPLOY_DIR_IMAGE}/${type}/tools/${UBOOT_CFGOUT}
            fi
        done
        unset j
    done
    unset i

}

# Modify the layout of u-boot to adding hse support using the following script.
# Currentlly, the board version of EVB is rev 1.0 and RDB2 is rev 2.0, they need
# different hse firmware version to coorperate with the board version, and these
# two boards will use same board version in future.

HSE_LOCAL_FIRMWARE_EVB_BIN ?= ""
HSE_LOCAL_FIRMWARE_RDB2_BIN ?= ""

do_compile_append() {

	unset i j
	for config in ${UBOOT_MACHINE}; do
		cp ${B}/tools/s32gen1_secboot.sh ${B}/${config}/tools/s32gen1_secboot.sh
		chmod +x ${B}/${config}/tools/s32gen1_secboot.sh

		i=$(expr $i + 1);
		for type in ${UBOOT_CONFIG}; do
			j=$(expr $j + 1)
			if  [ $j -eq $i ]; then

				if [ "${config}" = "${S32G274AEVB_UBOOT_DEFCONFIG_NAME}" ]; then
					if [ -n "${HSE_LOCAL_FIRMWARE_EVB_BIN}" ] && [ -e "${HSE_LOCAL_FIRMWARE_EVB_BIN}" ]; then
						${B}/${config}/tools/s32gen1_secboot.sh -k ./keys_hse -d ${B}/${config}/u-boot-hse-${type}.s32 --hse ${HSE_LOCAL_FIRMWARE_EVB_BIN}
						cp ${B}/${config}/u-boot-hse-${type}.s32 ${B}/${config}/${UBOOT_BINARY}
						cp ${B}/${config}/u-boot-hse-${type}.s32 ${B}/${config}/u-boot-${type}.bin
					fi
				else
					if [ -n "${HSE_LOCAL_FIRMWARE_RDB2_BIN}" ] && [ -e "${HSE_LOCAL_FIRMWARE_RDB2_BIN}" ]; then
						${B}/${config}/tools/s32gen1_secboot.sh -k ./keys_hse -d ${B}/${config}/u-boot-hse-${type}.s32 --hse ${HSE_LOCAL_FIRMWARE_RDB2_BIN}
						cp ${B}/${config}/u-boot-hse-${type}.s32 ${B}/${config}/${UBOOT_BINARY}
						cp ${B}/${config}/u-boot-hse-${type}.s32 ${B}/${config}/u-boot-${type}.bin
					fi
				fi
			fi
		done
		unset j
	done
	unset i
}

COMPATIBLE_MACHINE_nxp-s32g = "nxp-s32g"
