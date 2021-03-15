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
DEPENDS_append = " libgcc virtual/${TARGET_PREFIX}gcc python dtc-native bison-native"

inherit nxp-u-boot-localversion

SRC_URI_prepend = "git://source.codeaurora.org/external/autobsps32/u-boot;protocol=https;branch=release/bsp27.0-2020.04 "

SRC_URI += "file://0001-s32v234-refactor-ddr-init.patch \
	file://0002-sja1105-make-firmware-const.patch \
	file://0003-s32-add-include-guard-for-S32_DEFAULT_IP.patch \
	file://0004-campps32v2-update-xrdc-documentation-reference.patch \
	file://0005-s32g-bluebox3-change-gigabit-phys-addresses.patch \
	file://0006-Kconfig-Factorize-S32-platforms-under-S32V2-and-S32_.patch \
	file://0007-s32-Make-SYS_TEXT_BASE-and-SYS_DATA_BASE-part-of-Kco.patch \
	file://0008-Remove-S32V344-platforms.patch \
	file://0009-dt-bindings-s32g274a-Add-LLCE-clocks-indexes.patch \
	file://0010-drivers-clk-s32g274a-Add-LLCE-clocks.patch \
	file://0011-drivers-clk-s32g274a-Place-LLCE-block-under-XBAR-clo.patch \
	file://0012-s32-gen1-ddrss-Define-an-empty-store_csr-function.patch \
	file://0013-s32-gen1-Init-SRAM-using-SRAMC.patch \
	file://0014-campps32v2-reduce-ddr-speed-to-400MHz.patch \
	file://0015-s32-gen1-Introduce-NXP_S32G2XX-and-NXP_S32R45-config.patch \
	file://0016-s32g2xx-siul2-Add-ids-for-S32G254A-and-S32G233A.patch \
	file://0017-s32g2-Report-derivative-name-instead-of-s32g274a.patch \
	file://0018-s32g2xx-Isolate-secondary-cluster-cores-for-s32g254-.patch \
	file://0019-s32g2xx-Reduce-SRAM-size-to-6MB-for-s32g233.patch \
	file://0020-s32g274rdb-Fix-compile-issue.patch \
	file://0021-s32-gen1-Set-SDHC_CLK-to-400MHz.patch \
	file://0022-s32-gen1-Correct-return-values.patch \
	file://0023-s32-gen1-Enable-arch_misc_init-call.patch \
	file://0024-s32-gen1-Clear-SWT-non-critical-faults.patch \
	file://0025-dm-core-support-reading-a-single-indexed-u32-value.patch \
	file://0026-doc-add-pinctrl-s32-documentation.patch \
	file://0027-pinctrl-add-s32-gen1-pinctrl-driver.patch \
	file://0028-s32-enable-the-s32-pinctrl-driver-by-default.patch \
	file://0029-s32-gen1-add-picntrl-nodes.patch \
	file://0030-doc-add-gpio-s32-documentation.patch \
	file://0031-gpio-add-s32-gen1-gpio-driver.patch \
	file://0032-s32-enable-gpio-s32-driver-and-cmd_gpio.patch \
	file://0033-s32-gen1-add-gpio-nodes.patch \
	file://0034-s32-gen1-remove-hardcoded-pinmuxing.patch \
	file://0035-s32-gen1-add-pinctrl-definitions.patch \
	file://0036-dts-s32-gen1-add-pinctrl-for-i2c-spi-qspi-sdhc-usb-g.patch \
	file://0037-s32-gen1-Start-U-Boot-in-DDR-and-skip-relocation-whe.patch \
	file://0038-configs-Use-one-config-for-all-S32G274A-derivatives-.patch \
	file://0039-s32gen1-mmc-Avoid-an-unused-variable-warning.patch \
	file://0040-s32gen1-mmc-Enable-MMC-HS400-support.patch \
	file://0041-s32gen1-dts-Use-only-fsl-s32gen1-usdhc-compatible.patch \
	file://0042-s32gen1-mmc-Fix-incorrect-modification-of-priv-sdhc_.patch \
	file://0043-s32gen1-mmc-Fix-_execute_tuning-function.patch \
	file://0044-s32-gen1-ddr-Upgrade-DDR-firmware-to-2020_06.patch \
	file://0045-s32-gen1-ddr-Add-support-for-inline-ECC.patch \
	file://0046-dtsi-s32ggen1-Remove-DDR-clocks.patch \
	file://0047-s32g274a-Enable-3200-MT-s-on-DDR-for-rev-2.patch \
	file://0048-mmc-fsl_esdhc_imx-Correctly-use-guarded-members.patch \
	file://0049-mmc-fsl_esdhc_imx-Guard-fsl_esdhc_execute_tuning.patch \
	file://0050-s32g_emu-Do-not-select-MMC_HS400_SUPPORT.patch \
	file://0051-Kconfig-s32gen1-Fix-ERRATUM_ERR050543-dependency.patch \
	file://0052-arch-s32g274aemu-Add-dts-file.patch \
	file://0053-s32g274aemu-Update-defconfig.patch \
	file://0054-s32-Make-sure-MMU-is-disabled-before-early_mmu_setup.patch \
	file://0055-s32g274-Replace-CONFIG_S32G274A-with-CONFIG_NXP_S32G.patch \
	file://0056-s32r45-Replace-CONFIG_S32R45-with-CONFIG_NXP_S32R45.patch \
	file://0057-s32g-mmu-setup-Correct-TLB-size.patch \
	file://0058-s32g_emu-Remove-periph-clock-initialization.patch \
	file://0059-s32g_emu-Remove-sram-initialization.patch \
	file://0060-s32g_emu-Remove-Linflex-clock-initialization.patch \
	file://0061-s32g_emu-Don-t-wait-for-DFS_PORTRESET-updates.patch \
	file://0062-s32gen1-Update-SRAM-memory-layout.patch \
	file://0063-s32g-Correct-SDRAM-Base-when-booting-with-ATF.patch \
	file://0064-s32g-clk-Add-SAR_ADC-clock.patch \
	file://0065-lmb-Increase-the-number-of-allowed-logical-memory-bl.patch \
	file://0066-dts-s32r45evb-fix-gpio-node-compatible-string.patch \
	file://0067-s32r45-qspi-QSPI-on-S32R45-works-at-133-Mhz.patch \
	file://0068-s32g274aevb-Update-qspi-ivt-params-for-S32G274A-Rev1.patch \
	file://0069-s32-pinctrl-fix-SPI-pin-configs.patch \
	file://0070-efi_loader-Make-sure-copy_fdt-will-use-valid-address.patch \
	file://0071-s32-pinctrl-Fix-incorrect-mux-on-PE_07-for-pfe1.patch \
	file://0072-s32gen1-Disable-HS400-support-if-not-running-at-1.8V.patch \
	file://0073-xen-env-Update-env-to-assign-ttyLF0-to-Xen-and-hvc0-.patch \
	file://0074-xen-env-Refactor-environment-variables.patch \
	file://0075-secboot-support-standard-hse-fw-version.patch \
	file://0076-s32-ethernet-add-support-for-PFE-FW-0.9.3.patch \
	file://0077-pcie-Correct-range-for-S32-Gen1-PCIe-to-match-the-me.patch \
	file://0078-pcie-S32-Gen1-PCIe-uses-more-than-32b-in-the-memory-.patch \
	file://0079-pcie-S32-Gen1-code-did-not-set-the-limit-address-pro.patch \
	file://0080-pcie-S32-Gen-1-and-S32G-device-trees-had-bad-PCIe-ma.patch \
	file://0081-pcie-Configure-pcie-ranges.patch \
	file://0082-pcie-e1000-Enable-E1000-support.patch \
	file://0083-drivers-net-phy-Use-Aquantia-driver-for-AQR113C.patch \
"

SRCREV = "6f1223272a76516b04d5f22c2667a4dd64810443"
SRC_URI[sha256sum] = "4e80caf195787c76639675712492230d090fe2eb435fd44491d653463485e30c"

SCMVERSION = "y"
LOCALVERSION = ""
PACKAGE_ARCH = "${MACHINE_ARCH}"
UBOOT_INITIAL_ENV = ""

USRC ?= ""
S = '${@oe.utils.conditional("USRC", "", "${WORKDIR}/git", "${USRC}", d)}'

# Enable Arm Trusted Firmware
SRC_URI += " \
    ${@bb.utils.contains('IMAGE_INSTALL', 'arm-trusted-firmware', 'file://0001-defconfig-add-support-of-ATF-for-rdb2-boards.patch', '', d)} \
"

# For now, only rdb2 boards support ATF, this function will be fixed when new ATF supported boards added.
do_install_append() {

    if [ -n "${ATF_IMAGE}" ]; then
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

COMPATIBLE_MACHINE_nxp-s32g2xx = "nxp-s32g2xx"
