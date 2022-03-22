require linux-yocto-nxp-s32g2xx.inc

KBRANCH_nxp-s32g2xx  = "v5.10/standard/preempt-rt/nxp-sdk-5.10/nxp-s32g2xx"

LINUX_VERSION_nxp-s32g2xx ?= "5.10.x"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI_append_nxp-s32g2xx = " \
        file://0001-dt-bindings-crypto-hse-fix-MU-node-example.patch \
        file://0002-docs-crypto-hse-update-kerneldoc.patch \
        file://0003-gmac-s32-Fixed-a-bug-related-to-spliting-the-FCS-in-.patch \
        file://0004-drivers-perf-nxp-s32-ddr-perf-Clear-counters-after-e.patch \
        file://0005-drivers-perf-nxf-s32-ddr-perf-Sort-include-statement.patch \
        file://0006-Documentation-Add-ddr_gpr-device-tree-node-bindings.patch \
        file://0007-dts-s32cc-Add-DDR_GPR-syscon-node.patch \
        file://0008-drivers-perf-nxf-s32-ddr-perf-Enable-counter0-interr.patch \
        file://0009-drivers-perf-nxp-s32-ddr-perf-Save-counter-status-wh.patch \
        file://0010-drivers-perf-nxf-s32-ddr-perf-Stop-all-counters-on-c.patch \
        file://0011-drivers-perf-nxf-s32-ddr-perf-Check-if-IRQ-triggered.patch \
"

