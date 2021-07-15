require linux-yocto-nxp-s32g2xx.inc

KBRANCH_nxp-s32g2xx  = "v5.10/standard/nxp-sdk-5.4/nxp-s32g2xx"

LINUX_VERSION_nxp-s32g2xx ?= "5.10.x"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI_append_nxp-s32g2xx = " \
	file://nxp-s32g2xx-kernel.cfg \
"
