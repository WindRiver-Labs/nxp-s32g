# Copyright 2019-2020 NXP

DESCRIPTION = "ARM Trusted Firmware"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://license.rst;md5=1dd070c98a281d18d9eefd938729b031"

DEPENDS += "dtc-native xxd-native"
DEPENDS += "openssl-native"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

# ATF repository
URL ?= "git://git.trustedfirmware.org/TF-A/trusted-firmware-a.git;protocol=https"
BRANCH ?= "master"
SRC_URI = "${URL};branch=${BRANCH}"
SRCREV ?= "c158878249f1bd930906ebd744b90d3f2a8265f1"
SRC_URI[sha256sum] = "15d263b62089b46375effede12a1917cd7b267b93dd97c68fd5ddbd1dddede07"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:" 
SRC_URI += " \
    file://0001-Fix-fiptool-build-error.patch \
    file://bsp31/rc1/0001-s32g-Compiling-skeleton.patch \
    file://bsp31/rc1/0002-s32g-Add-basic-UART-console-support.patch \
    file://bsp31/rc1/0003-s32g-Reserve-DRAM-area-through-XRDC.patch \
    file://bsp31/rc1/0004-s32g-Update-DDR0-size.patch \
    file://bsp31/rc1/0005-s32g-Fix-primary-secondary-A53-cpu-masks.patch \
    file://bsp31/rc1/0006-s32g-Add-dummy-console-flush-function.patch \
    file://bsp31/rc1/0007-s32g-Set-SMPEN-bit-on-U-Boot-s-behalf.patch \
    file://bsp31/rc1/0008-s32g-Basic-GIC-initialization.patch \
    file://bsp31/rc1/0009-s32g-Update-includes-after-upstream-master-pull.patch \
    file://bsp31/rc1/0010-s32g-Move-BL31-after-BL33-in-the-SRAM-layout.patch \
    file://bsp31/rc1/0011-s32g-psci-Code-refactoring.patch \
    file://bsp31/rc1/0012-s32g-Add-PM-callbacks.patch \
    file://bsp31/rc1/0013-s32g-Enable-RTC-interrupt.patch \
    file://bsp31/rc1/0014-s32g-Correctly-parametrize-add_define_val.patch \
    file://bsp31/rc1/0015-s32g-Simulator-fixups-for-PSCI-System-Suspend.patch \
    file://bsp31/rc1/0016-s32g-Do-not-memset-cpu-context-on-warm-boot.patch \
    file://bsp31/rc1/0017-s32g-Update-generic-timer-frequency.patch \
    file://bsp31/rc1/0018-s32g-Clean-up-secure-interrupt-list.patch \
    file://bsp31/rc1/0019-s32g-Low-hanging-code-cleanup.patch \
    file://bsp31/rc1/0020-s32g-Boot-up-secondary-cores.patch \
    file://bsp31/rc1/0021-s32g-Replace-wfe-with-wfi-in-secondary-holding-pen.patch \
    file://bsp31/rc1/0022-s32g-Correct-generic-timer-frequency.patch \
    file://bsp31/rc1/0023-s32g-Remove-simulator-specific-code-from-console-sup.patch \
    file://bsp31/rc1/0024-s32g-Force-some-shift-operation-results-to-be-unsign.patch \
    file://bsp31/rc1/0025-s32g-Add-missing-includes-in-platform.mk.patch \
    file://bsp31/rc1/0026-s32g-Separate-defines-from-C-function-declarations-i.patch \
    file://bsp31/rc1/0027-s32g-Update-SRAM-base-address.patch \
    file://bsp31/rc1/0028-s32g-Add-basic-pinmuxing-and-clocks.patch \
    file://bsp31/rc1/0029-s32g-Add-BL2-stubs.patch \
    file://bsp31/rc1/0030-s32g-bl2-Add-ncore-support.patch \
    file://bsp31/rc1/0031-s32g-Set-Isolation-Enable-bit-for-primary-A53-cluste.patch \
    file://bsp31/rc1/0032-s32g-Move-pinmuxing-clocks-uart-init-in-BL2.patch \
    file://bsp31/rc1/0033-s32g-Reorganize-low-level-code-between-BL2-and-BL31.patch \
    file://bsp31/rc1/0034-s32g-Init-SRAM-before-use.patch \
    file://bsp31/rc1/0035-s32g-Fix-DFS-initialization-routine.patch \
    file://bsp31/rc1/0036-s32g-Serial-console-setup.patch \
    file://bsp31/rc1/0037-s32g-Load-BL31-from-BL2.patch \
    file://bsp31/rc1/0038-s32g-Redo-SRAM-layout.patch \
    file://bsp31/rc1/0039-s32g-Init-SRAM-area-for-BL31.patch \
    file://bsp31/rc1/0040-s32g-Skip-PRAM-and-XRDC-init.patch \
    file://bsp31/rc1/0041-s32g-Enable-DDR-clock.patch \
    file://bsp31/rc1/0042-s32g-Restore-Isolation-Enable-bit-in-Ncore.patch \
    file://bsp31/rc1/0043-s32g-Fix-compilation-issue-in-release-mode.patch \
    file://bsp31/rc1/0044-s32g-Add-pin-muxing-for-SDHC.patch \
    file://bsp31/rc1/0045-s32g-Enable-clock-for-SDHC.patch \
    file://bsp31/rc1/0046-s32g274a-clocks-Add-support-for-a53-clock-scaling.patch \
    file://bsp31/rc1/0047-s32g-Remove-VIRTUAL_PLATFORM-definition.patch \
    file://bsp31/rc1/0048-s32g-Move-secondary-core-initialization-to-BL31.patch \
    file://bsp31/rc1/0049-s32g-Temporarily-stop-secondary-cores.patch \
    file://bsp31/rc1/0050-s32g-Exclude-s32g_xrdc.c-from-build.patch \
    file://bsp31/rc1/0051-s32g-Add-BL33-SRAM-area-to-the-MMU-tables.patch \
    file://bsp31/rc1/0052-s32g-Move-MC_RGM-definitions-to-a-separate-header.patch \
    file://bsp31/rc1/0053-s32g-Add-DDR-initialization-driver.patch \
    file://bsp31/rc1/0054-s32g-Extend-virtual-address-space-to-36-bit.patch \
    file://bsp31/rc1/0055-s32g-Remove-relocate-to-PRAM-code.patch \
    file://bsp31/rc1/0056-s32g-Place-BL31-in-DDR.patch \
    file://bsp31/rc1/0057-s32g-Do-not-init-SRAM-area-for-BL31-any-longer.patch \
    file://bsp31/rc1/0058-s32g-Add-support-for-determining-the-reset-cause.patch \
    file://bsp31/rc1/0059-s32g274a-pm-Add-clock-gating-support-for-peripherals.patch \
    file://bsp31/rc1/0060-ssram-Init-the-first-1KB-of-Standby-SRAM.patch \
    file://bsp31/rc1/0061-ddrss-Save-a-predefined-list-of-CSRs-to-Standby-SRAM.patch \
    file://bsp31/rc1/0062-ddrss-Add-support-to-enter-I-O-LP3-Retention-Mode.patch \
    file://bsp31/rc1/0063-ddrss-Add-suport-for-resuming-from-I-O-LP3-Retention.patch \
    file://bsp31/rc1/0064-s32g274a-Init-SRAM-using-eDMA.patch \
    file://bsp31/rc1/0065-s32g274a-Update-call-of-non-existent-s32g_sram_init.patch \
    file://bsp31/rc1/0066-s32g274a-io-Duplicate-the-generic-io_memmap.c.patch \
    file://bsp31/rc1/0067-s32g274a-io-edma-Load-the-bl31-image-using-eDMA.patch \
    file://bsp31/rc1/0068-s32g274a-Fix-DDR-firmware-for-boards-with-4GB-RAM.patch \
    file://bsp31/rc1/0069-s32g274a-Add-Ncore-to-the-EL3-BL31-MMU.patch \
    file://bsp31/rc1/0070-s32g274a-Fix-40-bit-secondary-core-release-addr.patch \
    file://bsp31/rc1/0071-s32g274a-Fix-secondary-core-reset-sequence.patch \
    file://bsp31/rc1/0072-s32g274a-ncore-Simplify-macros-in-compliance-with-th.patch \
    file://bsp31/rc1/0073-s32g274a-Reconfigure-Ncore-before-starting-secondary.patch \
    file://bsp31/rc1/0074-s32g274a-Minor-refactoring-of-core-reset-code.patch \
    file://bsp31/rc1/0075-s32g274a-Define-HW_ASSISTED_COHERENCY.patch \
    file://bsp31/rc1/0076-s32g274a-Move-BL33-to-EL1.patch \
    file://bsp31/rc1/0077-s32g274a-Allow-ns-EL1-to-access-ICC_SRE-SRE-bit.patch \
    file://bsp31/rc1/0078-Revert-s32g-Temporarily-stop-secondary-cores.patch \
    file://bsp31/rc1/0079-s32g274a-Fix-MC_ME-partition-initialization.patch \
    file://bsp31/rc1/0080-s32g274a-Move-bl31-at-the-top-of-the-first-2GB.patch \
    file://bsp31/rc1/0081-s32g-Update-license-text.patch \
    file://bsp31/rc1/0082-s32g274a-clock-Set-SDHC_CLK-source-to-PERIPH_DFS3-in.patch \
    file://bsp31/rc1/0083-s32g274a-Enable-generic-timer.patch \
    file://bsp31/rc1/0084-s32g274a-Add-mmc-driver-and-io_mmc-interface.patch \
    file://bsp31/rc1/0085-s32g274a-Use-io_mmc-instead-of-io_memmap-for-loading.patch \
    file://bsp31/rc1/0086-s32g274a-bl2-Also-load-bl33-from-sd-mmc.patch \
    file://bsp31/rc1/0087-i2c-Add-i2c-pinmuxing.patch \
    file://bsp31/rc1/0088-i2c-Add-i2c-clock.patch \
    file://bsp31/rc1/0089-i2c-I2C-generic-driver.patch \
    file://bsp31/rc1/0090-Add-dts-support.patch \
    file://bsp31/rc1/0091-i2c-Get-data-from-dts.patch \
    file://bsp31/rc1/0092-s32g-Build-minimal-SSRAM-Bootstrap-code-as-BL1.patch \
    file://bsp31/rc1/0093-tbbr_img_def_exp-Add-ids-for-S32G-Standby-SRAM-image.patch \
    file://bsp31/rc1/0094-s32g-Add-back-io_memmap-interface.patch \
    file://bsp31/rc1/0095-s32g-Load-IVT_ABC-and-BOOTSTRAP_CODE-images-from-MMC.patch \
    file://bsp31/rc1/0096-s32g-Load-IVT_ABC-and-BOOTSTRAP_CODE-images-from-SRA.patch \
    file://bsp31/rc1/0097-s32g-ddrss-Simplify-signature-of-ddrss_to_normal_mod.patch \
    file://bsp31/rc1/0098-s32g-Remove-resume-path-code-from-BL2.patch \
    file://bsp31/rc1/0099-s32g-Save-CSRs-in-SSRAM-in-the-space-between-IVT-and.patch \
    file://bsp31/rc1/0100-s32g-ssram_bl1-Re-enable-DDR-and-jump-to-BL31.patch \
    file://bsp31/rc1/0101-s32g-Add-support-for-MMC_CMD-17.patch \
    file://bsp31/rc1/0102-s32g-Round-up-the-lengths-of-images-on-MMC-to-MMC_BL.patch \
    file://bsp31/rc1/0103-s32g-Avoid-a-warning-about-platform-setup-being-alre.patch \
    file://bsp31/rc1/0104-platform.mk-Ensure-DTC-version-is-1.4.6-or-above.patch \
    file://bsp31/rc1/0105-s32g-bl3-Register-linflex-uart.patch \
    file://bsp31/rc1/0106-s32g-Add-eMMC-support-to-the-uSDHC-driver.patch \
    file://bsp31/rc1/0107-s32g-Build-the-FIP-image-by-default-and-add-BL2-to-i.patch \
    file://bsp31/rc1/0108-s32g274a-Add-skeleton-function-for-pwr_domain_off.patch \
    file://bsp31/rc1/0109-s32g274a-Revert-s32g274a-Define-HW_ASSISTED_COHERENC.patch \
    file://bsp31/rc1/0110-s32g274a-Remove-unnecessary-condition.patch \
    file://bsp31/rc1/0111-s32g274a-Fix-panic-printing.patch \
    file://bsp31/rc1/0112-s32g274a-Add-RTC-interrupt.patch \
    file://bsp31/rc1/0113-gitignore-Add-vim-temporary-files.patch \
    file://bsp31/rc1/0114-s32g-Replace-all-s32g275-mentions-with-s32g274a.patch \
    file://bsp31/rc1/0115-s32g-mc_me-Correct-pconf-and-pupb-value-calculation.patch \
    file://bsp31/rc1/0116-s32g-mc_me-Add-a-setter-getter-for-OSSE-bit.patch \
    file://bsp31/rc1/0117-lib-add-crc8-algorithm.patch \
    file://bsp31/rc1/0118-pmic-Add-VR5510-driver.patch \
    file://bsp31/rc1/0119-s32g274a-Enable-VR5510-driver.patch \
    file://bsp31/rc1/0120-s32g274a-Implement-pwr_domain_suspend-callback.patch \
    file://bsp31/rc1/0121-s32-gen1-Add-WKPU-initial-driver.patch \
    file://bsp31/rc1/0122-s32g274a-Add-bl31sram-stage.patch \
    file://bsp31/rc1/0123-Revert-s32g-ssram_bl1-Re-enable-DDR-and-jump-to-BL31.patch \
    file://bsp31/rc1/0124-Revert-s32g-Build-minimal-SSRAM-Bootstrap-code-as-BL.patch \
    file://bsp31/rc1/0125-s32gen1-Remove-BL1-images.patch \
    file://bsp31/rc1/0126-s32gen1-Add-bl31-SSRAM-stage.patch \
    file://bsp31/rc1/0127-s32g-Shift-BL31-MMC-offset.patch \
    file://bsp31/rc1/0128-s32g-Fix-compile-time-error-from-unaligned-struct.patch \
    file://bsp31/rc1/0129-s32g-Add-fiptool-dependency-on-bl2.patch \
    file://bsp31/rc1/0130-s32g-Increase-BL33-MMC-size.patch \
    file://bsp31/rc1/0131-lib-Replace-crc8-implementation-to-avoid-license-con.patch \
    file://bsp31/rc1/0132-s32g-Add-PSCI-system_reset-callback.patch \
    file://bsp31/rc1/0133-s32g-Remove-s32g_ncore_isol_cluster0-duplicate.patch \
    file://bsp31/rc1/0134-s32g-Use-saved-entry-point-for-secondary-cores.patch \
    file://bsp31/rc1/0135-s32g-psci-Isolate-access-to-s32g_core_release_var.patch \
    file://bsp31/rc1/0136-s32g-bl31-Start-secondary-cores-only-on-request.patch \
    file://bsp31/rc1/0137-s32g-Implement-CPU-off.patch \
    file://bsp31/rc1/0138-s32g-Append-DTB-to-BL2-before-adding-it-to-the-FIP-i.patch \
    file://bsp31/rc1/0139-s32g-Add-BL31-DTB-to-FIP-image.patch \
    file://bsp31/rc1/0140-s32g-Automatically-call-mkimage.patch \
    file://bsp31/rc1/0141-s32g-Load-BL31-and-BL33-from-a-FIP-image.patch \
    file://bsp31/rc1/0142-s32g-Validate-the-size-of-the-FIP-image.patch \
    file://bsp31/rc1/0143-s32g-Ensure-BL2-does-not-overwrite-BootROM-data-in-S.patch \
    file://bsp31/rc1/0144-s32g-Add-support-for-reads-of-length-unaligned-to-MM.patch \
    file://bsp31/rc1/0145-s32g-Add-memmap-backend-for-FIP-and-refactor-s32g274.patch \
    file://bsp31/rc1/0146-s32g-platform.mk-Fix-check_dtc_version.patch \
    file://bsp31/rc1/0147-s32g-Change-the-directory-structure-and-filename-pre.patch \
    file://bsp31/rc1/0148-s32g-Init-SRAM-for-BL33.patch \
    file://bsp31/rc1/0149-s32g-Avoid-warning-about-platform-setup-already-bein.patch \
    file://bsp31/rc1/0150-s32g-bl31sram-Disable-MMU-before-enabling-DDR-retent.patch \
    file://bsp31/rc1/0151-s32g-Implement-resume-half-of-PSCI_SYSTEM_SUSPEND.patch \
    file://bsp31/rc1/0152-s32g-Save-and-restore-GIC-context.patch \
    file://bsp31/rc1/0153-s32g-Isolate-PMIC-initialization-code.patch \
    file://bsp31/rc1/0154-s32g-Runtime-determined-suspend-master-core.patch \
    file://bsp31/rc1/0155-s32g-Fix-EL3-FIQ-enablement.patch \
    file://bsp31/rc1/0156-s32g-psci-Keep-in-sync-ncore-settings-and-A53-cluste.patch \
    file://bsp31/rc1/0157-s32g274ardb-Implement-system-off-callback.patch \
    file://bsp31/rc1/0158-s32g-Reset-RTC-during-resume-procedure.patch \
    file://bsp31/rc1/0159-vr5510-Save-instance-name-in-DDR.patch \
    file://bsp31/rc1/0160-s32g-Reinitialize-I2C-at-every-pmic-initialization.patch \
    file://bsp31/rc1/0161-s32g-Reset-WKPU-status-after-every-suspend.patch \
    file://bsp31/rc1/0162-s32g-Initialize-SRAM-using-SRAM-controller.patch \
    file://bsp31/rc1/0163-s32g274ardb-Use-GPIO166-as-external-wake-up-source.patch \
    file://bsp31/rc1/0164-psci-Add-PSCI_MIGRATE_INFO_TYPE-stub.patch \
    file://bsp31/rc1/0165-s32g-i2c-Replace-i2c-driver.patch \
    file://bsp31/rc1/0166-s32g-makefile-Allow-variables-to-be-overridden-from-.patch \
    file://bsp31/rc1/0167-s32g-Compilation-fixes.patch \
    file://bsp31/rc1/0168-s32g-Use-A53-to-clear-SRAM-areas-that-are-not-aligne.patch \
    file://bsp31/rc1/0169-s32g-Load-BL31-SRAM-stage-right-before-entering-in-s.patch \
    file://bsp31/rc1/0170-s32g-Load-BL33-at-0x340a0000.patch \
    file://bsp31/rc1/0171-s32g-Change-FIP_MMC_OFFSET-to-0x3400.patch \
    file://bsp31/rc1/0172-s32g-Load-u-boot-built-with-OF_SEPARATE.patch \
    file://bsp31/rc1/0173-s32g-Check-BL33-and-its-dtb-after-loading-them.patch \
    file://bsp31/rc1/0174-s32g-Disable-PMIC-during-cold-and-warm-boot.patch \
    file://bsp31/rc1/0175-s32g-dtb-Add-clocks-bindings.patch \
    file://bsp31/rc1/0176-s32g-dtb-Add-clocks-modules.patch \
    file://bsp31/rc1/0177-s32g-Add-memory-pool-interface.patch \
    file://bsp31/rc1/0178-s32g-wkpu-Correct-reg-property-interpretation.patch \
    file://bsp31/rc1/0179-s32g-Port-clock-driver-from-U-Boot.patch \
    file://bsp31/rc1/0180-s32g-Clock-initializer-based-on-DTB.patch \
    file://bsp31/rc1/0181-st-scmi-clk-Fix-compilation-warning.patch \
    file://bsp31/rc1/0182-s32g-Add-SCMI-clocks-over-SMC.patch \
    file://bsp31/rc1/0183-s32g-i2c-Correct-reg-property-interpretation.patch \
    file://bsp31/rc1/0184-s32g-Isolate-DDR-clock-settings.patch \
    file://bsp31/rc1/0185-s32g-scmi-clk-Implement-disable-operation.patch \
    file://bsp31/rc1/0186-scmi-base-Add-implementation-for-reset-agent-configu.patch \
    file://bsp31/rc1/0187-s32g274a-Reset-clocks-for-an-agent.patch \
    file://bsp31/rc1/0188-s32g-fdt-Enable-clocks-over-SCMI.patch \
    file://bsp31/rc1/0189-Boot-OP-TEE-during-BL32-stage.patch \
    file://bsp31/rc1/0190-Pass-U-boot-s-DTB-address-to-OP-TEE.patch \
    file://bsp31/rc1/0191-dt-bindings-s32g274a-Add-LLCE-clocks-indexes.patch \
    file://bsp31/rc1/0192-drivers-clk-s32g274a-Add-LLCE-clocks.patch \
    file://bsp31/rc1/0193-s32-gen1-Set-SDHC_CLK-to-400MHz.patch \
    file://bsp31/rc1/0194-plat-s32g-Place-BL33-in-DDR.patch \
    file://bsp31/rc1/0195-s32g-Refactor-UART-driver.patch \
    file://bsp31/rc1/0196-s32g-Define-platform-crash-callbacks.patch \
    file://bsp31/rc1/0197-s32g274a-rev1-clock-Use-666.-6-MHZ-for-DDR_CLK.patch \
    file://bsp31/rc1/0198-s32g274a-Upgrade-DDR-firmware.patch \
    file://bsp31/rc1/0199-s32g-Adapt-DDR-retention-mode-glue-code-for-firmware.patch \
    file://bsp31/rc1/0200-s32g274a-rev2-clock-Use-800-MHZ-for-DDR_CLK.patch \
    file://bsp31/rc1/0201-s32g274a-Enable-3200-MT-s-on-DDR-for-rev-2.patch \
    file://bsp31/rc1/0202-dt-bindings-s32g274a-Add-WKPU-boot-select.patch \
    file://bsp31/rc1/0203-dts-s32g274a-Move-I2C-and-WKPU-to-S32GEN1.patch \
    file://bsp31/rc1/0204-dts-s32gen1-Select-long-warm-boot.patch \
    file://bsp31/rc1/0205-s32g274a-Add-support-for-long-boot.patch \
    file://bsp31/rc1/0206-s32g-clk-Add-SAR_ADC-clock.patch \
    file://bsp31/rc1/0207-s32g-Use-S32G_DRAM_INLINE_ECC-make-flag-to-enable-EC.patch \
    file://bsp31/rc1/0208-s32g-ddr-Add-a-way-to-skip-memory-initialization.patch \
    file://bsp31/rc1/0209-s32g-ddr-ECC-management-for-STR-use-case.patch \
    file://bsp31/rc1/0210-s32g-Replace-DMA-copy-using-core-copy-operations.patch \
    file://bsp31/rc1/0211-s32g-QSPI-boot-support.patch \
    file://bsp31/rc1/0212-dt-bindigns-s32g-Define-QSPI-clock-frequencies.patch \
    file://bsp31/rc1/0213-dts-s32g-Enable-QSPI-clock.patch \
    file://bsp31/rc1/0214-s32g-Set-QSPI-clock-based-on-SoC-revision.patch \
    file://bsp31/rc1/0215-s32g-mmc-Fix-while-loops-and-remove-unneeded-udelay.patch \
    file://bsp31/rc1/0216-s32g274-clk-scmi-Use-S32G-instead-of-S32G274.patch \
    file://bsp31/rc1/0217-clk-s32gen1-Correct-GMAC-SGMII-clock.patch \
    file://bsp31/rc1/0218-s32g-clk-Check-clock-state-before-returning-errors.patch \
    file://bsp31/rc1/0219-s32g-build-Specify-MKIMAGE-binary-as-build-parameter.patch \
    file://bsp31/rc1/0220-dt-bindings-s32g-Add-SCMI-reset-IDs.patch \
    file://bsp31/rc1/0221-s32g-Add-SCMI-reset-domain-implementation.patch \
    file://bsp31/rc1/0222-s32g-clk-Align-with-U-Boot-names.patch \
    file://bsp31/rc1/0223-s32g-clk-Align-with-U-Boot-clock-implementation.patch \
    file://bsp31/rc1/0224-clk-s32gen1-Update-SDHC-clock-frequency-to-400MHz.patch \
    file://bsp31/rc1/0225-tf-a-fix-boot-flow-from-eMMC.patch \
    file://bsp31/rc1/0226-s32gen1-uart-Suppress-printing-to-shared-console.patch \
    file://bsp31/rc1/0227-Add-partitions-to-SCMI-Reset-Domains.patch \
    file://bsp31/rc1/0228-s32gen1-Stop-registering-RTC-interrupt.patch \
    file://bsp31/rc1/0229-s32g-Enable-MMU-and-cache-in-BL2.patch \
    file://bsp31/rc1/0230-s32g-Copy-from-SD-QSPI-only-FIP-image-size.patch \
    file://bsp31/rc1/0231-s32g-Disable-PMIC-before-clocks-init.patch \
    file://bsp31/rc1/0232-s32g_storage-detect-boot-source-at-runtime.patch \
    file://bsp31/rc1/0233-bl2_el3-resume-bl31-earlier-when-waking-up-from-stan.patch \
    file://bsp31/rc1/0234-s32g-Fix-QSPI-debug-image-loading.patch \
    file://bsp31/rc1/0235-s32-Enable-workaround-for-ARM-errata-1530924.patch \
    file://bsp31/rc1/0236-s32g-minimal-initialization-during-resume-path.patch \
    file://bsp31/rc1/0237-s32g-Move-PMIC-WDG-programming-after-initializations.patch \
    file://bsp31/rc1/0238-s32g-Prevent-reset-escalation.patch \
    file://bsp31/rc1/0239-s32g274a-Add-dts-fixup-as-part-of-LPDDR4-ERR050543.patch \
    file://bsp31/rc1/0240-s32g-Report-min-and-max-frequencies-for-clocks-over-.patch \
    file://bsp31/rc1/0241-s32g-Add-minimal-support-for-DFS.patch \
    file://bsp31/rc1/0242-s32g-Add-OCOTP-driver-for-S32GEN1-platforms.patch \
    file://bsp31/rc1/0243-dts-Add-OCOTP-nodes-for-S32G.patch \
    file://bsp31/rc1/0244-s32g-Apply-VDD_CORE-adjustment.patch \
    file://bsp31/rc1/0245-s32g-reset-GPR-and-A53-generic-timers-in-early-boot-.patch \
    file://bsp31/rc1/0246-s32g-Remove-DDR-frequency-for-S32G274A-Rev-1.patch \
    file://bsp31/rc1/0247-s32g-Remove-QSPI-frequency-for-S32G274A-Rev-1.patch \
    file://bsp31/rc1/0248-Revert-s32g-Set-QSPI-clock-based-on-SoC-revision.patch \
    file://bsp31/rc1/0249-s32g3-clk-Add-support-for-GMAC-clocks.patch \
    file://bsp31/rc1/0250-s32g3-Add-support-for-8-cores.patch \
    file://bsp31/rc1/0251-s32g3-Add-fdt-file.patch \
    file://bsp31/rc1/0252-s32g-Add-s32g2-platform.patch \
    file://bsp31/rc1/0253-clk-s32g3-Update-core-frequency-to-1.3GHz.patch \
    file://bsp31/rc1/0254-s32g-clk-Rename-s32g274a_scmi_ids.patch \
    file://bsp31/rc1/0255-s32g-Move-s32g2-SRAMC-specific-in-platform-directory.patch \
    file://bsp31/rc1/0256-s32g3-Add-SRAMC-support.patch \
    file://bsp31/rc1/0257-arm64-Workaround-for-S32G2-erratum-ERR050481.patch \
    file://bsp31/rc1/0258-plat-s32g2-Enable-workaround-for-ERR050481-erratum.patch \
    file://bsp31/rc1/0259-plat-s32g2-Enable-workaround-for-ERR010493-erratum.patch \
    file://bsp31/rc1/0260-plat-s32g2-Enable-workaround-for-ERR008821-erratum.patch \
    file://bsp31/rc1/0261-plat-s32g2-Enable-workaround-for-ERR050764-erratum.patch \
    file://bsp31/rc1/0262-arm64-A53-erratum-836870-should-be-applied-on-r0p4.patch \
    file://bsp31/rc1/0263-s32g-ddr-Remove-unused-ddrss_firmware.c-file.patch \
    file://bsp31/rc1/0264-s32g-ddr-Update-to-the-1.4-version-of-S32CT.patch \
    file://bsp31/rc1/0265-s32g2-Update-DDR-firmware-to-2020_06_SP2-version.patch \
    file://bsp31/rc1/0266-s32g_storage-read-an-store-boot_source.patch \
    file://bsp31/rc1/0267-s32gen1-dts-add-i2c0-node.patch \
    file://bsp31/rc1/0268-s32g_storage-enable-boot-configuration-read-from-EEP.patch \
    file://bsp31/rc1/0269-s32g-Fix-compilation-errors.patch \
    file://bsp31/rc2/0001-s32g2-Move-PMIC-watchdog-disablement-from-BL31-to-BL.patch \
    file://bsp31/rc2/0002-s32g-add-MMC-and-QSPI-offsets-as-makefile-parameters.patch \
    file://bsp31/rc2/0003-clk-s32g-Remove-unnecessary-partition-blocks-for-cor.patch \
    file://bsp31/rc3/0001-nxp-s32g-Enable-workarounds-for-A53-r0p4-errata.patch \
    file://bsp31/rc3/0002-nxp-s32g-Separated-platform_def.h-for-S32G2-and-S32G.patch \
    file://bsp31/rc3/0003-nxp-s32g-Correct-MPIDR-serialization-for-S32G3.patch \
    file://bsp31/rc3/0004-s32g3-Enable-enable-hotplug-for-CPUs-4-7.patch \
    file://bsp31/rc3/0005-s32g3-Add-A53-and-M7-cores-to-SCMI-reset-layer.patch \
    file://bsp31/rc3/0006-s32g-Move-BL32_BASE-to-avoid-overlapping-with-BL33.patch \
    file://bsp31/rc3/0007-s32g-bl2-Add-static-checks-for-BL31-BL32-BL33-overla.patch \
    file://bsp31/rc3/0008-s32g-clk-Continue-even-if-failed-to-set-one-of-defau.patch \
    file://bsp31/rc3/0009-fdts-fsl-s32g274a-Append-missing-properties-instead-.patch \
    file://bsp31/rc3/0010-s32-gen1-Remove-Rev-1-of-SoC-references.patch \
    file://bsp31/rc3/0011-s32g-Correct-A53-kick-off.patch \
    file://bsp31/rc3/0012-s32g3-Initialize-DIRU1.patch \
    file://bsp31/rc3/0013-s32g-add-support-for-reading-FIP-from-a-defined-memo.patch \
    file://bsp31/rc3/0014-s32g-add-check-for-NULL-pointer-to-fix-coverity-issu.patch \
    file://bsp31/rc3/0015-s32g-Correct-A53-reset-procedure.patch \
    file://bsp31/rc3/0016-s32g-Add-an-accessory-function-for-lockstep-check.patch \
    file://bsp31/rc3/0017-s32g-Disable-half-of-the-cores-when-running-in-locks.patch \
    file://bsp31/rc3/0018-s32g-Turn-off-all-M7-cores-during-platform-suspend.patch \
    file://bsp31/rc3/0019-s32g2-Update-DDR-firmware-to-2020_06_SP2-Update-1.patch \
    file://bsp31/rc3/0020-s32g2-Add-ERRATA_S32G2_050543-DDR-Derating-macro.patch \
    file://bsp31/rc3/0021-s32g-Add-s32g3-DDR-code-and-separate-it-from-s32g2.patch \
    file://bsp31/rc3/0022-s32g-xrdc-Remove-unused-code.patch \
    file://bsp31/rc3/0023-s32g-Change-memory-areas-of-BL31-and-BL33.patch \
    file://bsp31/rc3/0024-s32g-bl2-Inject-atf-reserved-memory-node.patch \
    file://bsp31/rc4/0001-Move-S32G-support-in-S32-folder.patch \
    file://bsp31/rc4/0002-s32g-ddr-fix-RETENTION_ADDR-value.patch \
    file://bsp31/rc4/0003-s32g3-Initialize-SRAM-based-on-S32G2-SRAMC.patch \
    file://bsp31/rc4/0004-s32g3-emu-Add-DDR-driver.patch \
    file://bsp31/rc4/0005-s32g-emu-Skip-STR-related-features.patch \
    file://bsp31/rc4/0006-s32g3-emu-Adapt-LinFlex.patch \
    file://bsp31/rc4/0007-s32g-emu-Skip-image-loading-from-MMC.patch \
    file://bsp31/rc4/0008-s32g-emu-Skip-MMU-enablement-during-BL2.patch \
    file://bsp31/rc4/0009-s32g-emu-Skip-BL31-BL33-loading.patch \
    file://bsp31/rc4/0010-drivers-s32g-fp-Improve-precision.patch \
    file://bsp31/rc4/0011-s32g3-Correct-XBAR-clock-frequency.patch \
    file://bsp31/rc4/0012-s32g3-emu-Correct-GIC-redistributor-address.patch \
    file://bsp31/rc4/0013-s32g-emu-Skip-PMIC-OCOTP-WKPU-initialization.patch \
    file://bsp31/rc4/0014-s32gen1-dts-remove-clock-rate-for-early-configured-c.patch \
    file://bsp31/rc4/0015-s32gen1-select-A53-maximum-clock-frequency-based-on-.patch \
    file://bsp31/rc5/0001-s32g-Define-an-array-that-can-be-used-as-temporary-s.patch \
    file://bsp31/rc5/0002-s32g-SRAMC-refactoring.patch \
    file://bsp31/rc5/0003-s32g3-Correct-the-way-SRAMC-addresses-are-calculated.patch \
    file://bsp31/rc5/0004-s32g3-Correct-GICR-base-address.patch \
    file://bsp31/rc5/0005-s32g3-Correct-SRAM-ranges.patch \
    file://bsp31/rc5/0006-s32g3-fdts-Add-VR5510-on-RDB-board.patch \
    file://bsp31/rc5/0007-s32g-Apply-SVS-settings-for-S32G2-only.patch \
    file://bsp31/rc5/0008-s32g3-Correct-DDR-settings.patch \
    file://bsp31/rc5/0009-s32g3-mc_me-Correct-the-way-A53-cluster-is-enabled.patch \
    file://bsp31/rc5/0010-s32g3-Use-A53-max-frequencies-for-test-samples.patch \
    file://bsp31/rc5/0011-s32g3-Detect-cluster-based-on-core-id.patch \
    file://bsp31/rc5/0012-s32g-don-t-copy-the-entire-FIP-image-to-SRAM.patch \
    file://bsp31/rc5/0013-s32g3-ddr-Add-ECC_OFF-configuration-mode.patch \
    file://bsp31/rc5/0014-s32g-ddr-Add-S32CTv1.4-Update-2-code.patch \
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
