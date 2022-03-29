DESCRIPTION = "NXP LLCE FIRMWARES"
PROVIDES += "llce"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LIENCES.txt;md5=324d5bf9404f8bf2b01cfc66037447f6"

inherit deploy

do_configure[noexec] = "1"
do_compile[noexec] = "1"


SRC_URI = " \
	file://${LLCE_LOCAL_FIRMWARE_DIR} \
	file://${LLCE_LOCAL_FIRMWARE_DIR_S32G3} \
	file://LIENCES.txt \
"

# Tell yocto not to bother stripping our binaries, especially the firmware
# since 'aarch64-fsl-linux-strip' fails with error code 1 when parsing the firmware
# ("Unable to recognise the format of the input file")
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_SYSROOT_STRIP = "1"

S = "${WORKDIR}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

LLCE_FW_PLAT_FOLDERS ?= "${LLCE_FW_S32G2} ${LLCE_FW_S32G3}"

do_install() {

	for folder in ${LLCE_FW_PLAT_FOLDERS}; do

		mkdir -p "${FW_INSTALL_DIR}/${folder}"
		if [ "${folder}" = "${LLCE_FW_S32G3}" ]; then
			src_dir=${LLCE_LOCAL_FIRMWARE_DIR_S32G3}
		else
			src_dir=${LLCE_LOCAL_FIRMWARE_DIR}
		fi

		dst_dir=${FW_INSTALL_DIR}/${folder}
		for bin in ${LLCE_FW_BIN_LIST}; do
			install -D "${src_dir}/${bin}" "${dst_dir}/${bin}"
		done
	done

}

do_deploy() {

	for folder in ${LLCE_FW_PLAT_FOLDERS}; do

        	install -d ${DEPLOYDIR}/${folder}
	        src_dir=${FW_INSTALL_DIR}/${folder}
        	dst_dir=${DEPLOYDIR}/${folder}

	        for bin in ${LLCE_FW_BIN_LIST}; do
        	        install -m 0644 "${src_dir}/${bin}" "${dst_dir}/${bin}"
	        done
	done

}


pkg_postinst_ontarget_${PN} () {

bins="dte.bin frpe.bin ppe_tx.bin ppe_rx.bin"
for bin in ${bins}; do
        if [ -f "/lib/firmware/${bin}" ]; then
                continue
        fi

	if grep -q "s32g3" /sys/firmware/devicetree/base/compatible ; then
                ln -s /lib/firmware/llce_fw_s32g3/${bin} /lib/firmware/${bin}
        else
                ln -s /lib/firmware/llce_fw_s32g2/${bin} /lib/firmware/${bin}
        fi

done

mod_list="llce_can llce_core llce_mailbox"
for mod in $mod_list; do
        if grep -wq "^$mod" /proc/modules ; then
                rmmod $mod;
        fi
done

for mod in $mod_list; do
        modprobe $mod
done

}

# do_package_qa throws error "QA Issue: Architecture did not match"
# when checking the firmware
do_package_qa[noexec] = "1"
do_package_qa_setscene[noexec] = "1"

FILES_${PN} += "/lib/firmware/${LLCE_FW_S32G2}/${FW_INSTALL_DTE_NAME}"
FILES_${PN} += "/lib/firmware/${LLCE_FW_S32G2}/${FW_INSTALL_FRPE_NAME}"
FILES_${PN} += "/lib/firmware/${LLCE_FW_S32G2}/${FW_INSTALL_PPE-TX_NAME}"
FILES_${PN} += "/lib/firmware/${LLCE_FW_S32G2}/${FW_INSTALL_PPE-RX_NAME}"
FILES_${PN} += "/lib/firmware/${LLCE_FW_S32G3}/${FW_INSTALL_DTE_NAME}"
FILES_${PN} += "/lib/firmware/${LLCE_FW_S32G3}/${FW_INSTALL_FRPE_NAME}"
FILES_${PN} += "/lib/firmware/${LLCE_FW_S32G3}/${FW_INSTALL_PPE-TX_NAME}"
FILES_${PN} += "/lib/firmware/${LLCE_FW_S32G3}/${FW_INSTALL_PPE-RX_NAME}"

COMPATIBLE_MACHINE_nxp-s32g = "nxp-s32g"
