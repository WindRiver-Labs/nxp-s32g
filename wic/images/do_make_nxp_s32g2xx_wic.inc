do_make_nxp_s32g2xx_wic() {
	cd ${DEPLOY_DIR_IMAGE}
	for i in {1..10}
	do
		if [ ! -f ${IMAGE_LINK_NAME}.wic ]; then
			sleep 1
		else
			cp ${IMAGE_LINK_NAME}.wic ${IMAGE_LINK_NAME}-evb.wic
			dd if=u-boot.s32-s32g274aevb of=${IMAGE_LINK_NAME}-evb.wic conv=notrunc bs=256 count=1 seek=0
			dd if=u-boot.s32-s32g274aevb of=${IMAGE_LINK_NAME}-evb.wic conv=notrunc bs=512 seek=1 skip=1
			# For the secure-boot, this file u-boot.s32-s32g274ardb2 does not exit
			if [ -e u-boot.s32-s32g274ardb2 ]; then
				cp ${IMAGE_LINK_NAME}.wic ${IMAGE_LINK_NAME}-rdb2.wic
				dd if=u-boot.s32-s32g274ardb2 of=${IMAGE_LINK_NAME}-rdb2.wic conv=notrunc bs=256 count=1 seek=0
				dd if=u-boot.s32-s32g274ardb2 of=${IMAGE_LINK_NAME}-rdb2.wic conv=notrunc bs=512 seek=1 skip=1
			fi
			rm -f ${IMAGE_LINK_NAME}.wic
			break
		fi
	done
}

addtask make_nxp_s32g2xx_wic after do_image_complete before do_build
