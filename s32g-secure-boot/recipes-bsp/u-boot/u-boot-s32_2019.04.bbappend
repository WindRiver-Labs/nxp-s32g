
# Using the secure boot branch for u-boot repo

SRC_URI = "git://source.codeaurora.org/external/autobsps32/u-boot;protocol=https;branch=hotfix/bsp25.0.hf1"

SRCREV = "849038b967222e355b0dbde916c8f958a55955cc"

SRC_URI += "file://0001-s32g274aevb-add-secure-boot-support.patch \
	    file://0001-drivers-net-dwc_eth_qos-correct-the-invalidation-des.patch"

do_deploy_append_nxp-s32g () {
	if [ -e u-boot-nxp-s32g-2019.04-r0.img ];then
		ln -sf u-boot-nxp-s32g-2019.04-r0.img u-boot.s32
	fi
}
