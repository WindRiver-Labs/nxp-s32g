require linux-yocto-nxp-s32g2xx.inc

KBRANCH_nxp-s32g2xx  = "v5.10/standard/nxp-sdk-5.10/nxp-s32g2xx"

LINUX_VERSION_nxp-s32g2xx ?= "5.10.x"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI_append_nxp-s32g2xx = " \
    file://rc5/0001-ethernet-gmac-Fix-missing-coherency-for-gmac-on-s32-.patch \
    file://rc5/0002-dts-Update-GMAC-on-s32-platform-to-be-dma-coherent.patch \
    file://rc5/0003-doc-Add-dma-coherent-property-to-s32-GMAC-documentat.patch \
    file://rc5/0004-can-llce-move-llce-can-utils-into-a-new-header.patch \
    file://rc5/0005-dt-bindings-Add-LLCE-logger-channel.patch \
    file://rc5/0006-mailbox-llce-Add-channel-for-can-logger.patch \
    file://rc5/0007-dtsi-mailbox-llce-Add-logger-interrupt.patch \
    file://rc5/0008-dt-bindings-mailbox-Document-LLCE-logger-interrupt.patch \
    file://rc5/0009-dt-bindings-mailbox-Document-LLCE-logger-channel.patch \
    file://rc5/0010-llce-logger-Use-mailbox-for-communication-with-llce-.patch \
    file://rc5/0011-dts-Adapt-LLCE-logger-node-to-latest-driver-design.patch \
    file://rc5/0012-dt-bindings-net-can-Update-LLCE-CAN-logger-documenta.patch \
    file://rc5/0013-can-llce-Add-logging-parameter.patch \
    file://rc5/0014-llce-core-Add-load_fw-parameter.patch \
    file://rc5/0015-llce-logger-include-id-and-flags-in-dump.patch \
    file://rc5/0016-llce-logger-simplify-the-way-the-log-is-generated.patch \
    file://rc5/0017-llce-share-status-memory-region-between-core-and-mai.patch \
    file://rc5/0018-dts-s32g-add-shared-memory-region-to-LLCE-mailbox.patch \
    file://rc5/0019-dt-bindings-mailbox-Update-LLCE-memory-regions.patch \
    file://rc5/0020-llce-logger-Add-hardware-interface-to-log.patch \
    file://rc5/0021-mailbox-Lazy-irq-request-for-llce-channels.patch \
    file://rc5/0022-mailbox-llce-Add-config_platform-parameter.patch \
    file://rc5/0023-llce-logger-Add-PM-ops.patch \
    file://rc5/0024-Revert-sram-Add-LLCE-sram-awareness.patch \
    file://rc5/0025-Revert-sram-Add-power-management-operations.patch \
    file://rc5/0026-Revert-dts-bindings-sram-Add-llce-sram-compatible.patch \
    file://rc5/0027-llce-Replace-memory-pools-with-mapped-areas.patch \
    file://rc5/0028-bindings-mfd-Add-shared-memory-to-LLCE-core.patch \
    file://rc5/0029-can-Add-hardware-timestamp-to-CAN-messages.patch \
    file://rc5/0030-can-Move-LLCE-CAN-and-Logger-drivers-into-a-new-fold.patch \
    file://rc5/0031-mailbox-can-Fix-LLCE_RELESE_RX_INDEX-typo.patch \
    file://rc5/0032-mailbox-llce-Use-16-channels-for-logger.patch \
    file://rc5/0033-can-llce-Move-RX-related-functionality-into-a-common.patch \
    file://rc5/0034-can-llce-logger-Provide-the-logs-over-SocketCAN.patch \
    file://rc5/0042-ethernet-dwmac-s32cc-Change-functions-to-static-to-f.patch \
    file://rc5/0043-hse-prevent-subsequent-requests-after-fatal-error.patch \
    file://rc5/0044-hse-remove-uio-component-from-crypto-driver.patch \
    file://rc5/0045-llce-can-Add-compatibility-with-LLCE-firmware-1.0.2-.patch \
    file://rc5/0046-gpio-s32gen1-Set-IRQ-chip-name-based-on-device-name.patch \
    file://rc5/0047-gpio-siul2-List-pads-and-EIRQ-controlls-as-registers.patch \
    file://rc5/0048-gpio-siul2-Make-use-of-devm_gpiochip_add_data.patch \
    file://rc5/0049-gpio-siul2-Allow-to-be-referenced-as-interrupt-contr.patch \
    file://rc5/0001-Revert-hse-remove-uio-component-from-crypto-driver.patch \
    file://0001-arch-arm64-s32g-disable-virtio_block-dts-node-by-def.patch \ 
    file://0002-s32gen1-pcie-Remove-duplicate-interrupt-resource-req.patch \
    file://0003-driver-pci-pci-s32gen1-remove-the-__init-macro.patch \ 
    file://0004-drivers-pci-s32gen1-fix-s32gen1_pcie_msi_handler-def.patch \ 
    file://0005-drivers-pci-s32gen1-fix-build-warning-of-unused-func.patch \ 
    file://0006-dts-s32g27a-pfe-move-reserve-memory-address-to-leave.patch \ 
    file://0007-drivers-mailbox-replace-mutex-with-spinlock-in-llce_.patch \ 
    file://0008-arch-arm64-s32g-add-skew-ps-property-for-ksz9031-phy.patch \ 
    file://0009-dts-Add-phys-to-PFE-to-allow-usage-of-SerDes-driver.patch \ 
    file://0010-Documentation-pfe-bindings-of-SerDes-phys.patch \ 
    file://0011-documentation-fsl-pfeng-PFE-controller-reset-support.patch \ 
    file://0012-dt-bindings-s32g274a-pfe-PFE-controller-reset-suppor.patch \ 
    file://0013-arch-arm64-dts-keep-i2c1-in-disabled-status.patch \
    file://0014-drivers-phy-s32gen1-serdes-drop-the-redundant-phy-id.patch \
    file://0015-drivers-qspi-replace-dev_info-with-dev_dbg-in-qspi_r.patch \
    file://0016-dts-s32g274a-rdb2-disable-ARQ107-phy-node-explicitly.patch \
    file://0017-drivers-pci-modify-the-config-judgement-to-fix-build.patch \
"
