# Copyright 2019-2020 NXP

DESCRIPTION = "ARM Trusted Firmware"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://license.rst;md5=1dd070c98a281d18d9eefd938729b031"

DEPENDS += "dtc-native xxd-native bc-native"
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
    file://bsp32/rc3/0001-s32-gen1-Clear-SWT-non-critical-faults.patch \
    file://bsp32/rc3/0002-s32g-Correct-mkimage-parameters.patch \
    file://bsp32/rc3/0003-plat-s32g-Report-the-cause-of-reset.patch \
    file://bsp32/rc3/0004-pinctrl-add-support-for-WKPU-pads-enablement.patch \
    file://bsp32/rc3/0005-wkpu-enable-wakeup-interrupts-before-suspend.patch \
    file://bsp32/rc3/0006-ddr-Update-configuration-for-increasing-r-w-bandwidt.patch \
    file://bsp32/rc4/0001-s32-Add-initial-s32-generic-platform-files.patch \
    file://bsp32/rc4/0002-s32-sram-Add-s32-generic-sram-files.patch \
    file://bsp32/rc4/0003-s32-linflex-Move-linflexuart-to-generic-layer.patch \
    file://bsp32/rc4/0004-s32-ncore-Move-ncore-files-to-generic-s32-layer.patch \
    file://bsp32/rc4/0005-s32-GIC-Move-common-GIC-definitions-to-generic-heade.patch \
    file://bsp32/rc4/0006-s32r-Add-initial-platform-files.patch \
    file://bsp32/rc4/0007-s32g-Define-firmware-welcome-strings.patch \
    file://bsp32/rc4/0008-s32-pinctrl-Move-common-pinctrl-to-s32-layer.patch \
    file://bsp32/rc4/0009-s32-drivers-Rename-drivers-nxp-s32g-to-drivers-nxp-s.patch \
    file://bsp32/rc4/0010-s32-dt-Move-s32g_dt-files-to-s32-generic-layer.patch \
    file://bsp32/rc4/0011-s32-clocks-Move-common-clock-functions-and-defines-t.patch \
    file://bsp32/rc4/0012-s32-bl_common-Move-s32g_early_plat_init-to-generic-l.patch \
    file://bsp32/rc4/0013-s32g-clocks-Replace-shared-serdes1_lane1-clocks-with.patch \
    file://bsp32/rc4/0014-s32r-bindings-Add-s32r45-clock-bindings.patch \
    file://bsp32/rc4/0015-s32r-clocks-Add-s32r45-clocks.patch \
    file://bsp32/rc4/0016-s32g-dts-Move-specific-serdes-clocks-from-s32gen1-de.patch \
    file://bsp32/rc4/0017-s32r-dts-Add-s32r45-device-tree.patch \
    file://bsp32/rc4/0018-s32-i2c-Make-s32g_i2c-generic.patch \
    file://bsp32/rc4/0019-s32-storage-Make-s32g_storage-generic.patch \
    file://bsp32/rc4/0020-s32-dfs-Move-common-dfs-defines-to-s32-generic-layer.patch \
    file://bsp32/rc4/0021-s32-mc_me-Move-common-mc_me-defines-to-s32-generic-l.patch \
    file://bsp32/rc4/0022-s32-mc_rgm-Add-common-s32_mc_rgm-header.patch \
    file://bsp32/rc4/0023-s32-bl2-Make-add_-_img_to_mem_params-functions-gener.patch \
    file://bsp32/rc4/0024-s32r-bl2-Implement-bl2_el3_early_platform_setup.patch \
    file://bsp32/rc4/0025-s32-bl2-Add-common-s32_mmap-table.patch \
    file://bsp32/rc4/0026-s32-ddr-Use-S32GEN1_DRAM_INLINE_ECC-define.patch \
    file://bsp32/rc4/0027-s32r-ddr-Add-s32r45-DDR-subsystem-specific-files.patch \
    file://bsp32/rc4/0028-s32r-bl2-Implement-bl2_el3_plat_arch_setup.patch \
    file://bsp32/rc4/0029-s32-errata-Make-errata-flags-generic.patch \
    file://bsp32/rc4/0030-s32r-errata-Apply-ERR050481-and-ERR050543.patch \
    file://bsp32/rc4/0031-s32-bl2-Move-bl2_plat_handle_post_image_load-to-gene.patch \
    file://bsp32/rc4/0032-s32-bl31-Move-common-bl31-functions-to-generic-layer.patch \
    file://bsp32/rc4/0033-s32-scmi-Move-generic-scmi-files-to-common-bl31-sour.patch \
    file://bsp32/rc4/0034-s32r-scmi-Add-scmi-clocks-ids.patch \
    file://bsp32/rc4/0035-s32r-bl31-Implement-bl31_platform_setup.patch \
    file://bsp32/rc4/0036-s32-scmi-Move-svc-and-reset-files-to-bl31-common-sou.patch \
    file://bsp32/rc4/0037-s32r-bindings-Add-s32r45-reset-bindings.patch \
    file://bsp32/rc4/0038-s32r-reset-Add-s32r45-reset-table.patch \
    file://bsp32/rc4/0039-s32-psci-Add-generic-psci-files.patch \
    file://bsp32/rc4/0040-s32r-psci-remove-dummy-s32r45-psci-implementations.patch \
    file://bsp32/rc4/0041-s32r-clocks-Add-GMAC1-SCMI-clocks.patch \
    file://bsp32/rc4/0042-s32-bl2-Move-SWT-faults-reset-to-common-layer.patch \
    file://bsp32/rc4/0043-s32-bl2-Move-reporting-of-reset-cause-to-common-laye.patch \
    file://bsp32/rc4/0044-driver-nxp-mmc-Allow-the-reading-of-not-aligned-regi.patch \
    file://bsp32/rc4/0045-plat-nxp-s32g-Change-FIP-offset-for-QSPI-and-SD-boot.patch \
    file://bsp32/rc4/0046-ddr-Add-ECC-Exclusion-mechanism-from-U-Boot.patch \
    file://bsp32/rc4/0047-bl2-Exclude-ECC-range-from-U-Boot-DT-nodes.patch \
    file://bsp32/rc4/0048-bl2-Change-compatible-of-U-Boot-DT-ddr-node.patch \
    file://bsp32/rc4/0049-fdt-Remove-scmi-U-boot-fdt-fixup.patch \
    file://bsp32/rc4/0050-plat-nxp-s32g-Change-FIP-offset-for-QSPI-and-SD-boot.patch \
    file://bsp32/rc5/0001-s32g-clk-Correct-the-rate-returned-by-the-fixed-cloc.patch \
    file://bsp32/rc5/0002-dt-bindings-s32gen1-Add-macros-for-DFS1-and-DFS3-rat.patch \
    file://bsp32/rc5/0003-s32g-Make-QPSI-clock-part-of-early-clocks.patch \
    file://bsp32/rc5/0004-s32g-Replace-FIP_-_OFFSET-with-FIP_IN-ON_-and-FIP_OF.patch \
    file://bsp32/rc5/0005-s32g-Use-PAGE_SIZE-for-MMU-entries-alignment.patch \
    file://bsp32/rc5/0006-s32g-Export-FIP-header-size-through-FIP_INFO_SRC.patch \
    file://bsp32/rc5/0007-s32-Copy-FIP-header-from-SRAM.patch \
    file://bsp32/rc5/0008-s32g-Reserve-SD-QSPI-space-for-DTB-based-on-its-size.patch \
    file://bsp32/rc5/0009-clk-Add-S32_SET_NEAREST_FREQ-mechanism.patch \
    file://bsp32/rc5/0010-plat-bl31-Replace-obsolete-S32G_HAS_HV-with-new-name.patch \
    file://bsp32/rc5/0011-s32-plat-Decrease-the-maximum-number-of-translation-.patch \
    file://bsp32/rc6/0001-mk-add-PLAT_-S32_PLAT_SOC-variable.patch \
    file://bsp32/rc6/0002-s32g2-add-s32g274ardb2-and-s32g2xxaevb-boards.patch \
    file://bsp32/rc6/0003-s32g3-add-s32g399ardb3-and-s32g3xxaevb-boards.patch \
    file://bsp32/rc6/0004-s32r-add-s32r45evb-board.patch \
    file://bsp32/rc6/0005-pmic-add-board-specific-pmic-operations.patch \
    file://bsp32/rc6/0006-s32-add-PATH-variables-across-Makefiles.patch \
    file://bsp32/rc7/0001-s32-Introduce-FIP_HDR_SIZE-makefile-variable.patch \
    file://bsp32/rc7/0002-s32g2-disable-vr5510-for-s32g2xxaevb.patch \
    file://bsp32/rc7/0003-s32-ddr-Add-IO-Retention-fix-firmware-code.patch \
    file://bsp32/rc7/0004-s32r-psci-Implement-pwr_domain_pwr_down_wfi-callback.patch \
    file://bsp32/rc7/0005-s32-Adapt-BL33-entry-point-and-dtb-location.patch \
    file://bsp32/rc10/0001-plat-nxp-s32-Force-boot_info.c-regeneration.patch \
    file://bsp32/rc10/0002-secboot-add-FIRC_FREQ-define.patch \
    file://bsp32/rc10/0003-secboot-add-support-for-atf-fip-boot.patch \
"

PLATFORM_nxp-s32g2xx = "s32g2xxaevb s32g274ardb2 s32g399ardb3"
BUILD_TYPE = "release"
ATF_S32G_ENABLE = "1"

EXTRA_OEMAKE += " \
                CROSS_COMPILE=${TARGET_PREFIX} \
                ARCH=${TARGET_ARCH} \
                BUILD_BASE=${B} \
                "

# FIXME: Allow linking of 'tools' binaries with native libraries
#        used for generating the boot logo and other tools used
#        during the build process.
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CPPFLAGS} ${BUILD_LDFLAGS}" \
                 HOSTLD="${BUILD_LD}" \
                 LIBPATH="${STAGING_LIBDIR_NATIVE}" \
                 HOSTSTRIP=true'

do_compile() {
	unset LDFLAGS
	unset CFLAGS
	unset CPPFLAGS

	for plat in ${PLATFORM}; do
		ATF_BINARIES="${B}/${plat}/${BUILD_TYPE}"
		oe_runmake -C ${S} PLAT=${plat} BL33="${DEPLOY_DIR_IMAGE}/${plat}/${UBOOT_BINARY}" MKIMAGE_CFG="${DEPLOY_DIR_IMAGE}/${plat}/tools/${UBOOT_CFGOUT}" all
	done
}

do_deploy() {
	install -d ${DEPLOY_DIR_IMAGE}
	for plat in ${PLATFORM}; do
		ATF_BINARIES="${B}/${plat}/${BUILD_TYPE}"
		cp -v ${ATF_BINARIES}/fip.s32 ${DEPLOY_DIR_IMAGE}/atf-${plat}.s32
	done
}

addtask deploy after do_compile

do_compile[depends] = "virtual/bootloader:do_install"

COMPATIBLE_MACHINE = "nxp-s32g2xx"
ALLOW_EMPTY_${PN} = "1"
