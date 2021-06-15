require linux-yocto-nxp-s32g2xx.inc

KBRANCH_nxp-s32g2xx  = "v5.10/standard/nxp-sdk-5.4/nxp-s32g2xx"

LINUX_VERSION_nxp-s32g2xx ?= "5.10.x"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI_append_nxp-s32g2xx = " \
    file://0001-qspi-s32r45evb-Enter-spi-mode-after-DTR-OPI-read.patch \
    file://0002-qspi-s32r45evb-doc-Document-new-dts-property.patch \
    file://0003-can-flexcan-Enable-clocks-on-resume-path.patch \
    file://0004-dts-s32g274ardb2-Disable-I2C1.patch \
    file://0005-spi-fsl-dspi-Restore-slave-pinmuxing-after-suspend.patch \
    file://0006-Revert-llce-can-Add-compatibility-with-LLCE-firmware.patch \
    file://0007-sdhci-esdhc-imx-s32-gen1-toggle-clocks-on-suspend-if.patch \
    file://0008-clocksource-stm-Correct-IRQ-affinity.patch \
    file://0009-clocksource-pit-Correct-IRQ-affinity.patch \
    file://0010-s32v234-dts-Add-missing-H264enc-interrupt.patch \
    file://0011-drivers-phy-nxp-free-custom-data-after-phy-work-queu.patch \
    file://0012-drivers-dspi-fsl-fix-dspi-transfer-hang-issue.patch \
    file://0013-drivers-spi-fsl-dspi-delete-the-unused-variable.patch \
    file://0014-dts-s32g274a-rdb2-disable-ARQ107-phy-node-explicitly.patch \
"
