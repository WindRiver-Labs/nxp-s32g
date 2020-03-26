MAKE_NXP_S32G2XX_WIC = "no"
MAKE_NXP_S32G2XX_WIC_nxp-s32g2xx = "yes"

do_make_nxp_s32g2xx_wic() {
	if [ ${MAKE_NXP_S32G2XX_WIC} = "yes" ]; then
		cd ${DEPLOY_DIR_IMAGE}
		for i in {1..10}
		do
			if [ ! -f ${IMAGE_LINK_NAME}.wic ]; then
				sleep 1
			else
				dd if=u-boot.s32 of=${IMAGE_LINK_NAME}.wic conv=notrunc bs=256 count=1 seek=0
				dd if=u-boot.s32 of=${IMAGE_LINK_NAME}.wic conv=notrunc bs=512 seek=1 skip=1
				break
			fi
		done
	fi
}

addtask make_nxp_s32g2xx_wic after do_image_complete before do_build
