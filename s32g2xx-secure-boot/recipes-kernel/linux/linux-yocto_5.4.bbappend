#
# Copyright (C) 2020 Wind River Systems, Inc.
#

#
# Rewrite this function to add signature node
#
fitimage_emit_section_kernel_nxp-s32g2xx() {

	kernel_csum=${KERNEL_CSUM_TYPE}
	if [ -n "${UBOOT_SIGN_ENABLE}" ] ; then
		kernel_sign_keyname="${UBOOT_SIGN_KEYNAME}"
	fi

	ENTRYPOINT="${UBOOT_ENTRYPOINT}"

	cat << EOF >> ${1}
                kernel-${2} {
                        description = "Linux kernel";
                        data = /incbin/("arch/arm64/boot/Image");
                        type = "kernel";
                        arch = "${UBOOT_ARCH}";
                        os = "linux";
                        compression = "none";
                        load = <${UBOOT_LOADADDRESS}>;
                        entry = <${ENTRYPOINT}>;
                        hash-1 {
                                algo = "${kernel_csum}";
                        };
                        signature-1 {
                                algo = "${kernel_csum},${KERNEL_RAS_TYPE}";
                                key-name-hint = "${kernel_sign_keyname}";
                        };
                };
EOF
}

#
# Rewrite this function to add signature node
#
fitimage_emit_section_dtb_nxp-s32g2xx() {

	dtb_csum=${KERNEL_CSUM_TYPE}
	if [ -n "${UBOOT_SIGN_ENABLE}" ] ; then
		dtb_sign_keyname="${UBOOT_SIGN_KEYNAME}"
	fi

	cat << EOF >> ${1}
                fdt-${2} {
                        description = "Flattened Device Tree blob";
                        data = /incbin/("${3}");
                        type = "flat_dt";
                        arch = "${UBOOT_ARCH}";
                        compression = "none";
			fdt-version = <1>;
                        hash-1 {
                                algo = "${dtb_csum}";
                        };
                        signature-1 {
                                algo = "${dtb_csum},${KERNEL_RAS_TYPE}";
                                key-name-hint = "${dtb_sign_keyname}";
                        };
                };
EOF
}

