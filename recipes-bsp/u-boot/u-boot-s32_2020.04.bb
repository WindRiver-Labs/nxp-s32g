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

SRC_URI:prepend = "git://source.codeaurora.org/external/autobsps32/u-boot;protocol=https;branch=release/bsp31.0-2020.04"

SRC_URI += " \
    file://0001-configs-s32g274aevb-add-HSE_SECBOOT-config-for-HSE-t.patch \
    file://0001-secboot-add-key-store-status-check-point-after-sys_i.patch \
    file://0001-Make-s32g274ardb2-and-s32g2xxaevb-support-ostree.patch \
    file://0001-scripts-mailmapper-python2-python3.patch \
    file://bsp32/rc1/0002-driver-clk-s32g3-Rename-S32G398A-to-S32G3.patch \
    file://bsp32/rc1/0003-arch-arm-dts-Rename-S32G398-to-S32G3.patch \
    file://bsp32/rc1/0004-Rename-target-s32g398a-to-s32g399a.patch \
    file://bsp32/rc1/0005-board-s32g3-Rename-DDR-folder-to-s32g3.patch \
    file://bsp32/rc1/0006-s32gen1-scmi-Use-one-clock-ID-for-GMAC-TS-clock.patch \
    file://bsp32/rc1/0007-bindings-clock-Use-one-clock-ID-for-GMAC-TS-clock.patch \
    file://bsp32/rc1/0008-dts-s32gen1-Remove-redundant-GMAC-clocks.patch \
    file://bsp32/rc1/0009-s32gen1-Enable-HS400-Enhanced-Strobe.patch \
    file://bsp32/rc1/0010-s32gen1-Correct-SRAM-initialization-algorithm.patch \
    file://bsp32/rc1/0011-s32-gen1-pcie-Check-if-SerDes-subsystem-is-present.patch \
    file://bsp32/rc1/0012-s32-gen1-pcie-doc-Document-new-dts-binding-entry.patch \
    file://bsp32/rc1/0013-Remove-S32V2-configs.patch \
    file://bsp32/rc1/0014-Remove-S32V2-boards.patch \
    file://bsp32/rc1/0015-Remove-S32V2-Kconfig-options.patch \
    file://bsp32/rc1/0016-Remove-S32V2-drivers.patch \
    file://bsp32/rc1/0017-s32gen1-Add-AHB-buffer-to-MMU-tables-when-needed-onl.patch \
    file://bsp32/rc2/0001-s32g-sramc-Don-t-subtract-SRAM-base-from-an-SRAM-off.patch \
    file://bsp32/rc2/0002-s32gen1-sramc-Adjust-SRAMC-range-to-delete-only-the-.patch \
    file://bsp32/rc2/0003-s32gen1-sram-Report-the-size-of-the-cleared-SRAM.patch \
    file://bsp32/rc2/0004-env-Allow-overriding-no-1-8-v-property-in-fdt-before.patch \
    file://bsp32/rc2/0005-s32gen1-cmu-Renamed-Expected-column-to-Expected-rang.patch \
    file://bsp32/rc2/0006-gmac1-s32gen1-Add-device-tree-bindings-for-gmac1.patch \
    file://bsp32/rc2/0007-gmac1-s32gen1-Introduce-gmac1-clocks-into-the-clockf.patch \
    file://bsp32/rc2/0008-gmac-s32gen1-Enable-gmac0-and-gmac1-to-work-at-the-s.patch \
    file://bsp32/rc2/0009-gmac-s32gen1-Fixed-unchecked-NULL-returns-error-code.patch \
"

SRCREV = "6286902c946f15b2b2ab66904a26d6e0e8748802"
SRC_URI[sha256sum] = "4e80caf195787c76639675712492230d090fe2eb435fd44491d653463485e30c"

SCMVERSION = "y"
LOCALVERSION = ""
PACKAGE_ARCH = "${MACHINE_ARCH}"
UBOOT_INITIAL_ENV = ""

USRC ?= ""
S = '${@oe.utils.conditional("USRC", "", "${WORKDIR}/git", "${USRC}", d)}'

# Enable Arm Trusted Firmware
SRC_URI += " \
    ${@bb.utils.contains('ATF_S32G_ENABLE', '1', 'file://0001-defconfig-add-support-of-ATF-for-rdb2-boards.patch', '', d)} \
"

# For now, only rdb2 boards support ATF, this function will be fixed when new ATF supported boards added.
do_install_append() {

    if [ -n "${ATF_S32G_ENABLE}" ]; then
        unset i j
        install -d ${DEPLOY_DIR_IMAGE}
        for config in ${UBOOT_MACHINE}; do
            i=$(expr $i + 1);
            for type in ${UBOOT_CONFIG}; do
                j=$(expr $j + 1)
                if  [ $j -eq $i ]; then
                        if [ "$type" = "${ATF_SUPPORT_TYPE}" ]; then
                            cp ${B}/${config}/u-boot.bin ${DEPLOY_DIR_IMAGE}/u-boot.bin
                            install -d ${DEPLOY_DIR_IMAGE}/tools
                            cp ${B}/${config}/tools/mkimage ${DEPLOY_DIR_IMAGE}/tools/mkimage
                            break
                        fi
                fi
            done
            unset j
        done
        unset i
    fi
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
					cp ${B}/${config}/u-boot-hse-${type}.s32 ${B}/${config}/u-boot.s32
					cp ${B}/${config}/u-boot-hse-${type}.s32 ${B}/${config}/u-boot-${type}.bin
				fi
			else
				if [ -n "${HSE_LOCAL_FIRMWARE_RDB2_BIN}" ] && [ -e "${HSE_LOCAL_FIRMWARE_RDB2_BIN}" ]; then
					${B}/${config}/tools/s32gen1_secboot.sh -k ./keys_hse -d ${B}/${config}/u-boot-hse-${type}.s32 --hse ${HSE_LOCAL_FIRMWARE_RDB2_BIN}
					cp ${B}/${config}/u-boot-hse-${type}.s32 ${B}/${config}/u-boot.s32
					cp ${B}/${config}/u-boot-hse-${type}.s32 ${B}/${config}/u-boot-${type}.bin
				fi
			fi
		fi
	done
	unset j
    done
    unset i
}

COMPATIBLE_MACHINE_nxp-s32g2xx = "nxp-s32g2xx"
