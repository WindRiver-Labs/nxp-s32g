ATF_S32G_ENABLE ??= ""
S32G_PLAT_nxp-s32g2xx ??= "rdb2 evb rdb3"
do_make_nxp_s32g2xx_wic() {
	cd ${DEPLOY_DIR_IMAGE}
	for i in {1..10}
	do
		if [ ! -f ${IMAGE_LINK_NAME}.wic ]; then
			sleep 1
		else
                        unset j
                        for plat in ${S32G_PLAT}; do
                                j=$(expr $j + 1);
                                type=`echo ${UBOOT_CONFIG} | awk -v "n=$j" '{print $n}'`
                                cp ${IMAGE_LINK_NAME}.wic ${IMAGE_LINK_NAME}-${plat}.wic
                                if [ -e atf-${type}.s32 ]; then
                                        dd if=atf-${type}.s32 of=${IMAGE_LINK_NAME}-${plat}.wic conv=notrunc bs=256 count=1 seek=0
                                        dd if=atf-${type}.s32 of=${IMAGE_LINK_NAME}-${plat}.wic conv=notrunc bs=512 seek=1 skip=1
                                else
                                        dd if=${UBOOT_BINARY}-${type} of=${IMAGE_LINK_NAME}-${plat}.wic conv=notrunc bs=256 count=1 seek=0
                                        dd if=${UBOOT_BINARY}-${type} of=${IMAGE_LINK_NAME}-${plat}.wic conv=notrunc bs=512 seek=1 skip=1
                                fi
                        done
                        unset j

			rm -f ${IMAGE_LINK_NAME}.wic
			break
		fi
	done
}

addtask make_nxp_s32g2xx_wic after do_image_complete before do_build

BOOTFS_EXTRA_CMD ?= "cp ustart.img evb.ustart.img; \
    dd if=${DEPLOY_DIR_IMAGE}/${UBOOT_BINARY}-s32g274aevb of=evb.ustart.img conv=notrunc bs=256 count=1 seek=0; \
    dd if=${DEPLOY_DIR_IMAGE}/${UBOOT_BINARY}-s32g274aevb of=evb.ustart.img conv=notrunc bs=512 seek=1 skip=1; \
    if [ -e ${DEPLOY_DIR_IMAGE}/${UBOOT_BINARY}-s32g274ardb2 ]; then \
        cp ustart.img rdb2.ustart.img; \
        if [ -n "${ATF_S32G_ENABLE}" ]; then \
            dd if=atf-s32g274ardb2.s32 of=rdb2.ustart.img conv=notrunc bs=256 count=1 seek=0; \
            dd if=atf-s32g274ardb2.s32 of=rdb2.ustart.img conv=notrunc bs=512 seek=1 skip=1; \
        else \
            dd if=${DEPLOY_DIR_IMAGE}/${UBOOT_BINARY}-s32g274ardb2 of=rdb2.ustart.img conv=notrunc bs=256 count=1 seek=0; \
            dd if=${DEPLOY_DIR_IMAGE}/${UBOOT_BINARY}-s32g274ardb2 of=rdb2.ustart.img conv=notrunc bs=512 seek=1 skip=1; \
        fi; \
        rm -f ustart.img; \
    fi \
"
