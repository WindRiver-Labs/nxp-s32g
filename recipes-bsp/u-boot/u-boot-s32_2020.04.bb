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
    file://bsp32/rc3/0001-s32-qspi-Use-QSPI_BOOT-instead-of-FLASH_BOOT.patch \
    file://bsp32/rc3/0002-soc-s32g3-Print-SoC-Revision.patch \
    file://bsp32/rc3/0003-Revert-s32gen1-Add-fdt_pcie_spis_fixup-in-env.patch \
    file://bsp32/rc3/0004-Kconfigs-s32-Use-a-convenient-default-value-for-SYS_.patch \
    file://bsp32/rc3/0005-s32-Remove-IMX-occurrences-from-S32-support.patch \
    file://bsp32/rc3/0006-mmc-fsl_esdhc_imx-Guard-the-access-to-scr-register.patch \
    file://bsp32/rc3/0007-s32-gen1-fccu-Guard-clear-of-SWT-non-critical-faults.patch \
    file://bsp32/rc3/0008-tools-s32gen1image-Group-all-CONFIG_-settings.patch \
    file://bsp32/rc3/0009-mkimage-s32gen1-Use-n-argument-for-a-configuration-f.patch \
    file://bsp32/rc3/0010-s32-gen1-clocks-Enable-CLK_PER-clock-for-SARADC.patch \
    file://bsp32/rc3/0011-s32-gen1-saradc-Add-DM-driver-for-SAR-ADC.patch \
    file://bsp32/rc3/0012-s32g274a-rdb-adc-identify-revision-of-32G-RDB-and-up.patch \
    file://bsp32/rc3/0013-arch-fsl-lsch2-Remove-unmaintained-fsl-lsch2.patch \
    file://bsp32/rc3/0014-sac58r-Remove-code-for-obsolete-sac58r-board.patch \
    file://bsp32/rc3/0015-mac57d5xx-Remove-code-for-obsolete-mac57d5xx-board.patch \
    file://bsp32/rc3/0016-s32v-Remove-CSE3-DCU-and-FEC-code-related-to-S32V.patch \
    file://bsp32/rc3/0017-imx-Remove-IMX-DMA-Channel-MUX-framework.patch \
    file://bsp32/rc3/0018-s32-remove-edma-controller-macros.patch \
    file://bsp32/rc3/0019-vf610-remove-changes-related-to-vf610.patch \
    file://bsp32/rc3/0020-Revert-efi_loader-Make-sure-copy_fdt-will-use-valid-.patch \
    file://bsp32/rc3/0021-Revert-cmd-Add-CUSTOM_CMD_FLASH-to-Kconfig.patch \
    file://bsp32/rc3/0022-Revert-dspi-Access-the-registers-of-the-appropriate-.patch \
    file://bsp32/rc3/0023-s32-gen1-fsl_dspi-Remove-non-DM_SPI-code-logic.patch \
    file://bsp32/rc3/0024-s32-pinctrl-move-MSCR-definitions-to-a-single-place.patch \
    file://bsp32/rc3/0025-ddr-Update-configuration-for-increasing-r-w-bandwidt.patch \
    file://bsp32/rc4/0001-misc-Add-SIUL2-NVRAM-driver-for-S32GEN1-SoC-s.patch \
    file://bsp32/rc4/0002-dts-s32gen1-Add-SIUL2-NVRAM-nodes.patch \
    file://bsp32/rc4/0003-arch-s32-gen1-Use-SIUL2-NVRAM-driver-to-print-SoC-s-.patch \
    file://bsp32/rc4/0004-board-s32-gen1-use-SIUL2-NVRAM-to-print-board-name.patch \
    file://bsp32/rc4/0005-pci-s32-gen1-Use-SIUL2-NVRAM-to-check-SerDes-presenc.patch \
    file://bsp32/rc4/0006-cpu-Add-CPU-driver-for-S32GEN1-platforms.patch \
    file://bsp32/rc4/0007-cmd-mp-Rename-cpu-command-to-mp.patch \
    file://bsp32/rc4/0008-dts-s32-gen1-Add-CPU-nodes.patch \
    file://bsp32/rc4/0009-cpu-s32gen1-Use-default-CPU-reporting-mechanism.patch \
    file://bsp32/rc4/0010-dts-s32gen1-Update-model-to-reflect-board-s-name.patch \
    file://bsp32/rc4/0011-board-s32gen1-Report-board-s-name-based-on-info-from.patch \
    file://bsp32/rc4/0012-tools-s32gen1image-Remove-duplicated-IVT-instance.patch \
    file://bsp32/rc4/0013-tools-imximage-Restrict-image-detection.patch \
    file://bsp32/rc4/0014-tools-s32gen1image-Correct-the-way-the-header-is-int.patch \
    file://bsp32/rc4/0015-tools-s32gen1image-Parse-configuration-file-during-i.patch \
    file://bsp32/rc4/0016-tools-s32gen1image-Add-MBR-field-for-SD-boot.patch \
    file://bsp32/rc4/0017-tools-s32gen1image-Reduce-storage-footprint.patch \
    file://bsp32/rc4/0018-tools-s32gen1image-Use-QSPI_BOOT-instead-of-FLASH_BO.patch \
    file://bsp32/rc4/0019-mkimage-s32gen1-Use-CONFIG_QSPI_BOOT-instead-of-CONF.patch \
    file://bsp32/rc4/0020-s32-Use-ARMv8-linker-file.patch \
    file://bsp32/rc4/0021-s32gen1-mp-Use-PCSI-CPU_ON-to-start-a-CPU-when-booti.patch \
    file://bsp32/rc4/0022-misc-sja1105-Make-configuration-part-of-the-driver.patch \
    file://bsp32/rc4/0023-boards-bluebox3-move-gpio-pinctrl-to-dts.patch \
    file://bsp32/rc4/0024-boards-s32g-remove-kind-of-setup_iomux-functions.patch \
    file://bsp32/rc4/0025-boards-s32gen1-remove-S32G274A-and-S32R45-simulators.patch \
    file://bsp32/rc4/0026-arch-s32-clean-up-siul2-headers.patch \
    file://bsp32/rc4/0027-serial-linflex-remove-non-DM-linflex-driver.patch \
    file://bsp32/rc4/0028-serial-linflex-add-compatible-string.patch \
    file://bsp32/rc4/0029-serial-linflex-fix-priv-lfuart-initialization.patch \
    file://bsp32/rc4/0030-serial-linflex-fix-baudrate-initialization.patch \
    file://bsp32/rc4/0031-serial-linflex-fix-compiler-warnings.patch \
    file://bsp32/rc4/0032-serial-linflex-add-optimisations-for-s32gen1-emulato.patch \
    file://bsp32/rc4/0033-serial-linflex-fix-pending-callback.patch \
    file://bsp32/rc4/0034-dts-s32-add-uart0-as-stdout-path.patch \
    file://bsp32/rc4/0035-s32-pinctrl-add-linflex-0-and-1-pin-configs.patch \
    file://bsp32/rc4/0036-dts-s32g-add-uart0-pinctrl-bindings.patch \
    file://bsp32/rc4/0037-dts-s32-emulator-set-serial1-as-stdout-path.patch \
    file://bsp32/rc4/0038-dts-s32-emulator-add-pinmuxing-for-serial1.patch \
    file://bsp32/rc4/0039-board-s32g-remove-CONFIG_BOARD_EARLY_INIT_F.patch \
    file://bsp32/rc4/0040-configs-s32-enable-dm_serial.patch \
    file://bsp32/rc4/0041-dts-s32g-add-u-boot-dm-pre-reloc-property-to-pinconf.patch \
    file://bsp32/rc4/0042-configs-add-TARGET_S32G274ARDB2-and-common-RDB-confi.patch \
    file://bsp32/rc4/0043-dts-add-s32g399ardb3-dts-and-a-common-s32gxxxardb.dt.patch \
    file://bsp32/rc4/0044-configs-add-S32G399ARDB3-support.patch \
    file://bsp32/rc4/0045-saradc-identify-revision-E-for-RDB3-platforms.patch \
    file://bsp32/rc4/0046-s32-gen1-Enable-ATF-boot-flow-by-default.patch \
    file://bsp32/rc4/0047-tools-s32gen1image-Remove-the-gap-between-APP-header.patch \
    file://bsp32/rc4/0048-s32-gen1-Remove-disablement-of-VR5510-PMIC.patch \
    file://bsp32/rc4/0049-s32g399ardb3-enable-default-ATF-boot-flow.patch \
    file://bsp32/rc4/0050-ddr-Remove-ECC-Exclusion-mechanism.patch \
    file://bsp32/rc4/0051-gmac-s32-Fixed-misleading-help-messages-shown-in-s32.patch \
    file://bsp32/rc4/0052-s32gen1-Remove-SDHC_REDUCED_MAP.patch \
    file://bsp32/rc4/0053-board-s32-gen1-Remove-all-DDR-subsystem-code.patch \
    file://bsp32/rc4/0054-configs-Remove-CONFIG_SYS_ERRATUM_ERR050543-from-RDB.patch \
    file://bsp32/rc4/0055-s32-Change-ft_fixup_ddr_polling-to-read-the-ddr-DT-n.patch \
    file://bsp32/rc4/0056-dts-s32-gen1-Move-ddr-node-to-s32-gen1.dtsi-file.patch \
    file://bsp32/rc4/0057-ddr-Remove-CONFIG_SYS_ERRATUM_ERR050543.patch \
    file://bsp32/rc4/0058-fdt-fixup-Remove-SCMI-clocks-fixup-logic.patch \
    file://bsp32/rc4/0059-scmi-Enable-clocks-over-SCMI-by-default.patch \
    file://bsp32/rc4/0060-tools-s32gen1image-Reserve-space-for-DCD-when-it-s-r.patch \
    file://bsp32/rc4/0061-tools-s32gen1image-Improve-SD-QSPI-layout.patch \
    file://bsp32/rc4/0062-tools-s32gen1image-Place-APP-header-as-the-last-comp.patch \
    file://bsp32/rc4/0063-tools-s32gen1image-Use-different-alignment-for-SD-an.patch \
    file://bsp32/rc4/0064-fdt-fixup-Remove-ft_fixup_cpu.patch \
    file://bsp32/rc5/0001-sram-remove-initsram-command.patch \
    file://bsp32/rc5/0002-s32gen1-remove-SRAM-initialization-algorithm.patch \
    file://bsp32/rc5/0003-s32gen1-remove-sram-dts-nodes-and-memory-ranges.patch \
    file://bsp32/rc5/0004-s32gen1-remove-rename-configs-related-to-SRAM.patch \
    file://bsp32/rc5/0005-timer-s32-Add-timer-support-for-S32-SoC.patch \
    file://bsp32/rc5/0006-fsl_dspi-Get-DSPI-clock-from-device-tree.patch \
    file://bsp32/rc5/0007-dts-s32-gen1-add-clock-bindings-for-linflex.patch \
    file://bsp32/rc5/0008-serial-linflex-get-clock-from-device-tree.patch \
    file://bsp32/rc5/0009-arch-s32-gen1-remove-mxc_get_clock.patch \
    file://bsp32/rc5/0010-tools-s32gen1image-Add-data-size-option.patch \
    file://bsp32/rc5/0011-tools-s32gen1image-Remove-FIP-awareness.patch \
    file://bsp32/rc5/0012-tools-s32gen1image-Print-IVT-location.patch \
    file://bsp32/rc5/0013-arm-s32-Remove-ARMV8_MULTIENTRY-and-ARMV8_SPIN_TABLE.patch \
    file://bsp32/rc5/0014-s32-Remove-CONFIG_S32_STANDALONE_BOOT_FLOW-config.patch \
    file://bsp32/rc5/0015-s32-Remove-S32_SKIP_RELOC-and-S32_ATF_BOOT_FLOW.patch \
    file://bsp32/rc5/0016-s32-remove-CONFIG_RUN_FROM_IRAM_ONLY.patch \
    file://bsp32/rc5/0017-Revert-s32-gen1-Enable-ATF-boot-flow-by-default.patch \
    file://bsp32/rc5/0018-Revert-s32g399ardb3-enable-default-ATF-boot-flow.patch \
    file://bsp32/rc5/0019-s32-remove-empty-arch_early_init_r-function.patch \
    file://bsp32/rc5/0020-s32gen1-sysreset-Use-PSCI-SYSTEM_RESET-function.patch \
    file://bsp32/rc5/0021-drivers-crypto-hse-Add-config-for-HSE-firmware-file.patch \
    file://bsp32/rc5/0022-tools-s32gen1image-Add-HSE-FW-file-to-image-layout.patch \
    file://bsp32/rc5/0023-tools-s32gen1image-Add-slot-for-HSE-SYS-image-to-ima.patch \
    file://bsp32/rc5/0024-tools-s32gen1image-Sort-images-component-based-on-of.patch \
    file://bsp32/rc5/0025-arch-s32-gen1-remove-imx-regs.h.patch \
    file://bsp32/rc5/0026-serial-linflexuart-skip-debug-uart-init.patch \
    file://bsp32/rc5/0027-serial-linflexuart-add-DEBUG_UART_LINFLEXUART.patch \
    file://bsp32/rc5/0028-arch-s32-call-debug_uart_init-from-arch_cpu_init.patch \
    file://bsp32/rc5/0029-serial-linflexuart-wait-for-TX-fifo-to-be-empty.patch \
    file://bsp32/rc5/0030-serial-linflexuart-enter-init-mode-before-setting-ba.patch \
    file://bsp32/rc5/0031-imx-Introduce-CONFIG_MACH_IMX.patch \
    file://bsp32/rc5/0032-configs-s32-set-default-console-to-ttyLF0.patch \
    file://bsp32/rc5/0033-qspi-Read-QSPI-clock-from-dts.patch \
    file://bsp32/rc5/0034-clk-Remove-driver-and-rely-on-SCMI.patch \
    file://bsp32/rc5/0035-dts-Remove-unused-clk-modules-nodes.patch \
    file://bsp32/rc5/0036-dts-Remove-all-fixed-clock-clk-nodes.patch \
    file://bsp32/rc5/0037-s32-gen1-soc-Remove-enable_i2c_clk.patch \
    file://bsp32/rc5/0038-clk-Remove-inclusion-of-clk-dt-bindings.patch \
    file://bsp32/rc5/0039-dt-bindings-Remove-s32-gen1-clocking-doc.patch \
    file://bsp32/rc5/0040-dt-bindings-Remove-all-non-SCMI-clock-include-files.patch \
    file://bsp32/rc5/0041-clk-Move-clk_utils-to-drivers-net-s32.patch \
    file://bsp32/rc5/0042-s32-Fix-loadfdt-call-to-fdt_override.patch \
    file://bsp32/rc5/0043-secboot-fix-ivt-read-write.patch \
    file://bsp32/rc6/0001-configs-Move-DM_-configs-in-Kconfig-files.patch \
    file://bsp32/rc6/0002-s32-remove-save_tlb.patch \
    file://bsp32/rc6/0003-s32-remove-MC_ME-function-related-to-secondary-cores.patch \
    file://bsp32/rc6/0004-s32-remove-CONFIG_MP-ifdefine-and-unused-variables.patch \
    file://bsp32/rc6/0005-s32-remove-lowlevel.S-and-use-default-armv8-version.patch \
    file://bsp32/rc6/0006-pcie-s32-move-necessary-defines-in-header-file.patch \
    file://bsp32/rc6/0007-net-pfeng-move-necessary-defines-in-header-file.patch \
    file://bsp32/rc6/0008-arch-s32-move-necessary-defines-in-header-file.patch \
    file://bsp32/rc6/0009-arch-s32-removed-unused-headers.patch \
    file://bsp32/rc7/0001-s32g3-Fix-compilation.patch \
    file://bsp32/rc7/0002-s32-sgmii-remove-SGMII_MIN_SOC_REV_SUPPORTED.patch \
    file://bsp32/rc7/0003-pfeng-remove-board-defines.patch \
    file://bsp32/rc7/0004-board-s32gr45evb-remove-not-useful-DWC_ETH_QOS_S32CC.patch \
    file://bsp32/rc7/0005-board-s32-gen1-remove-setup_iomux_uart-definitions.patch \
    file://bsp32/rc7/0006-board-s32-gen1-remove-include-guarded-by-CONFIG_TARG.patch \
    file://bsp32/rc7/0007-arch-s32-remove-siul.h.patch \
    file://bsp32/rc7/0008-s32-Initialize-MMU-after-relocation.patch \
    file://bsp32/rc7/0009-s32-env-Fix-empty-fdt_override-variable.patch \
    file://bsp32/rc7/0010-a53-gpr-Add-misc-driver-for-retrieving-lockstep-info.patch \
    file://bsp32/rc7/0011-siul2-nvram-Add-method-to-indentify-max-number-of-co.patch \
    file://bsp32/rc7/0012-s32-gen1-nvmem-Update-siul2-nvmem-documentation.patch \
    file://bsp32/rc7/0013-fdt-fixup-lockstep-Disable-half-cores-when-lockstep-.patch \
    file://bsp32/rc7/0014-s32-Remove-ncore-initialization.patch \
    file://bsp32/rc7/0015-s32-remove-cpu_mask.patch \
    file://bsp32/rc7/0016-s32-gen1-cpu-Move-defines-into-usage-source-file.patch \
    file://bsp32/rc7/0017-s32gen1-mmc-Fix-incorrect-setting-of-SD-Clock.patch \
    file://bsp32/rc7/0018-s32-move-startm7-command-into-a-separate-file.patch \
    file://bsp32/rc7/0019-s32-remove-SRAM-code.patch \
    file://bsp32/rc7/0020-s32-timer-add-timer_get_boot_us.patch \
    file://bsp32/rc7/0021-libfdt-Fix-signedness-comparison-warnings.patch \
    file://bsp32/rc7/0022-freescale-Enable-binman-for-S32GEN1-platforms.patch \
    file://bsp32/rc7/0023-s32-gen1-Replace-u-boot.s32-with-u-boot-s32.bin.patch \
    file://bsp32/rc7/0024-dts-s32gen1-Add-binman-node.patch \
    file://bsp32/rc7/0025-secboot-read-sysimg-location-from-ivt.patch \
    file://bsp32/rc7/0026-s32-Remove-CONFIG_S32.patch \
    file://bsp32/rc7/0027-s32-Remove-CONFIG_REMAKE_ELF-as-u-boot.elf-isn-t-use.patch \
    file://bsp32/rc7/0028-s32-Remove-CONFIG_MACH_TYPE.patch \
    file://bsp32/rc7/0029-s32-Make-CMD_CACHE-a-dependency-of-S32_GEN1.patch \
    file://bsp32/rc7/0030-s32-Remove-LOADADDR.patch \
    file://bsp32/rc7/0031-s32-Remove-CONFIG_OF_FDT.patch \
    file://bsp32/rc7/0032-s32-Make-OF_BOARD_SETUP-a-dependency-of-S32_GEN1.patch \
    file://bsp32/rc7/0033-s32-Remove-CONFIG_SYS_PIT_TIMER-from-include-configs.patch \
    file://bsp32/rc7/0034-s32-Remove-unused-CONFIG_MAX_CPUS.patch \
    file://bsp32/rc7/0035-s32-Remove-SECONDARY_CPU_BOOT_PAGE-and-CPU_RELEASE_A.patch \
    file://bsp32/rc7/0036-s32-Remove-unused-CONFIG_FSL_SMP_RELEASE_ALL-macro.patch \
    file://bsp32/rc7/0037-s32-Make-CONFIG_MP-a-dependency-of-CONFIG_S32_GEN1.patch \
    file://bsp32/rc7/0038-s32-Remove-unused-QSPI-macros.patch \
    file://bsp32/rc7/0039-s32-Remove-unused-CONFIG_SYS_UART_PORT-define.patch \
    file://bsp32/rc7/0040-s32-Use-booti-to-boot-Linux.patch \
    file://bsp32/rc7/0041-s32-Remove-unused-CONFIG_BOARD_EXTRA_ENV_SETTINGS.patch \
    file://bsp32/rc7/0042-s32-Remove-unused-CONFIG_DCU_EXTRA_ENV_SETTINGS.patch \
    file://bsp32/rc7/0043-s32-Remove-unused-INITRD_HIGH_DEFAULT.patch \
    file://bsp32/rc7/0044-s32-gen1-Remove-CONFIG_SYS_FSL_ESDHC_ADDR.patch \
    file://bsp32/rc7/0045-s32-gen1-Remove-CONFIG_FEC_XCV_TYPE.patch \
    file://bsp32/rc7/0046-s32-gen1-Remove-BCM-settings.patch \
    file://bsp32/rc7/0047-s32-gen1-Remove-Linflex-defines-leftovers.patch \
    file://bsp32/rc7/0048-s32-gen1-Remove-FEC-specific-macros.patch \
    file://bsp32/rc7/0049-s32-gen1-Remove-unused-CONFIG_SYS_TEXT_OFFSET.patch \
    file://bsp32/rc7/0050-scripts-Remove-unused-configs-from-config_whitelist..patch \
    file://bsp32/rc7/0051-s32-Replace-CONFIG_SYS_FSL-with-CONFIG_SYS_FLASH.patch \
    file://bsp32/rc8/0001-s32-gen1-env-Rename-u-boot-flashaddr-to-fip-flashadd.patch \
    file://bsp32/rc10/0001-tools-Initialize-S32GEN1-image-components.patch \
    file://bsp32/rc10/0002-include-configs-Correct-fip.s32-update-instructions.patch \
    file://bsp32/rc10/0003-configs-Fix-S32G274A-BlueBox-compilation.patch \
    file://bsp32/rc10/0004-board-s32g399ardb3-eth_fixup-fix-Aquantia-PHY-addr.patch \
    file://bsp32/rc10/0005-secboot-add-support-for-atf-fip-boot.patch \
    file://bsp32/rc10/0006-secboot-add-reserved-region-to-s32gen1image.patch \
    file://bsp32/rc10/0007-s32-serdes-hwconfig-Add-skip-option-to-hwconfig.patch \
    file://0001-Make-s32g274ardb2-and-s32g2xxaevb-support-ostree.patch \
    file://0001-scripts-mailmapper-python2-python3.patch \
    file://0001-Makefile-add-.cfgout-file-dependency-to-fix-atf-buil.patch \
    file://0001-tools-s32gen1_secboot-replace-u-boot.s32-with-u-boot.patch \
"

SRCREV = "6286902c946f15b2b2ab66904a26d6e0e8748802"
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

COMPATIBLE_MACHINE_nxp-s32g2xx = "nxp-s32g2xx"
