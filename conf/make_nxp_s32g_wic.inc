ATF_S32G_ENABLE ??= "1"
S32G_PLAT ??= "rdb2 evb rdb3"
do_make_nxp_s32g_wic() {
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

addtask make_nxp_s32g_wic after do_image_complete before do_build

BOOTFS_EXTRA_CMD ?= " \
    j=0; \
    for plat in ${S32G_PLAT}; do \
        j=$(expr $j + 1); \
        type=`echo ${UBOOT_CONFIG} | awk -v "n=$j" '{print $n}'`; \
        cp ustart.img ${plat}.ustart.img; \
        if [ -n "${ATF_S32G_ENABLE}" -a -e ${DEPLOY_DIR_IMAGE}/atf-${type}.s32 ]; then \
            dd if=${DEPLOY_DIR_IMAGE}/atf-${type}.s32 of=${plat}.ustart.img conv=notrunc bs=256 count=1 seek=0; \
            dd if=${DEPLOY_DIR_IMAGE}/atf-${type}.s32 of=${plat}.ustart.img conv=notrunc bs=512 seek=1 skip=1; \
        elif [ -e ${DEPLOY_DIR_IMAGE}/${UBOOT_BINARY}-${type} ]; then \
            dd if=${DEPLOY_DIR_IMAGE}/${UBOOT_BINARY}-${type} of=${plat}.ustart.img conv=notrunc bs=256 count=1 seek=0; \
            dd if=${DEPLOY_DIR_IMAGE}/${UBOOT_BINARY}-${type} of=${plat}.ustart.img conv=notrunc bs=512 seek=1 skip=1; \
        fi; \
    done; \
    rm -f ustart.img; \
"
