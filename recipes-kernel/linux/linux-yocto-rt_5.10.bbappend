require linux-yocto-nxp-s32g2xx.inc

KBRANCH_nxp-s32g2xx  = "v5.10/standard/preempt-rt/nxp-sdk-5.10/nxp-s32g2xx"

LINUX_VERSION_nxp-s32g2xx ?= "5.10.x"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI_append_nxp-s32g2xx = " \
    file://bsp31/rc5/0001-boot-dts-s32-gen1-Mark-generic-timer-interrupt-as-PP.patch \
    file://bsp31/rc5/0002-boot-dts-s32g3-Move-GIC-node-into-s32g398-device-tre.patch \
    file://bsp31/rc5/0003-gpio-siul2-Translate-EIRQ-number-before-mapping.patch \
    file://bsp31/rc5/0004-dts-s32g398-evb-Enable-additional-SWT-nodes.patch \
    file://bsp31/rc5/0005-dts-s32g-Enable-STM0-STM1-modules.patch \
    file://bsp31/rc5/0006-dts-s32g-evb-Enable-FTM0-FTM1.patch \
    file://bsp31/rc5/0007-Adapt-LLCE-SRAM-regions-for-s32g3.patch \
    file://bsp31/rc5/0008-llce-Interface-update-for-1.0.3-firmware.patch \
    file://bsp31/rc5/0009-can-llce-Use-short-packets-for-Classic-CAN-interface.patch \
    file://bsp31/rc5/0010-mailbox-llce-Use-sema42-to-synchronize-host-and-firm.patch \
    file://bsp31/rc5/0011-mfd-llce-core-Update-status-regs-location.patch \
    file://bsp31/rc5/0012-mailbox-llce-Send-config-commands-over-host0-interfa.patch \
    file://bsp31/rc5/0013-mailbox-llce-Correct-IRQ-enablement.patch \
    file://bsp31/rc5/0014-dts-s32g-Enable-llce-sram-regions-for-RDB-boards.patch \
    file://bsp31/rc5/0015-mailbox-llce-Interface-update-for-1.0.3-firmware.patch \
    file://bsp31/rc5/0016-uio-hse-move-driver-reserved-memory-to-DDR.patch \
    file://bsp31/rc5/0017-crypto-hse-enable-ahash-algorithms-by-default.patch \
    file://bsp31/rc5/0018-crypto-hse-minor-type-fixes-in-core-interface.patch \
    file://bsp31/rc5/0019-crypto-hse-search-for-next-channel-in-reverse.patch \
    file://bsp31/rc5/0020-crypto-hse-print-info-in-human-readable-format.patch \
    file://bsp31/rc5/0021-crypto-hse-fix-hwrng-to-handle-non-blocking-read.patch \
    file://bsp31/rc5/0022-dts-s32g-Allow-simpler-interrupt-pass-through-for-hy.patch \
    file://bsp31/rc5/0023-uio-hse-fix-reference-counter-logic.patch \
    file://bsp31/rc5/0024-spi-fsl-dspi-halt-the-module-after-a-new-message-tra.patch \
    file://0001-arch-arm64-s32g-disable-virtio_block-dts-node-by-def.patch \ 
    file://0002-s32gen1-pcie-Remove-duplicate-interrupt-resource-req.patch \
    file://0003-driver-pci-pci-s32gen1-remove-the-__init-macro.patch \ 
    file://0006-dts-s32g27a-pfe-move-reserve-memory-address-to-leave.patch \ 
    file://0007-drivers-mailbox-replace-mutex-with-spinlock-in-llce_.patch \ 
    file://0008-arch-arm64-s32g-add-skew-ps-property-for-ksz9031-phy.patch \ 
    file://0009-dts-Add-phys-to-PFE-to-allow-usage-of-SerDes-driver.patch \ 
    file://0010-Documentation-pfe-bindings-of-SerDes-phys.patch \ 
    file://0011-documentation-fsl-pfeng-PFE-controller-reset-support.patch \ 
    file://0012-dt-bindings-s32g274a-pfe-PFE-controller-reset-suppor.patch \ 
    file://0013-arch-arm64-dts-keep-i2c1-in-disabled-status.patch \
    file://0014-drivers-phy-s32gen1-serdes-drop-the-redundant-phy-id.patch \
    file://0016-dts-s32g274a-rdb2-disable-ARQ107-phy-node-explicitly.patch \
    file://0001-drivers-llce-mailbox-delete-DO_ONCE-call-for-llce_ca.patch \
    file://0002-drivers-llce_can-put-echo-skb-before-sending-message.patch \
    file://0001-drivers-serial-linflexuart-drop-write_atomic-callbac.patch \
    file://0001-drivers-cpypto-hse-rng-make-mutex-lock-unlock-operat.patch \
"
