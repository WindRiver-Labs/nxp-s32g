DESCRIPTION = "NXP IPCF FIRMWARES"
PROVIDES += "llce"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LIENCES.txt;md5=324d5bf9404f8bf2b01cfc66037447f6"

inherit deploy

do_configure[noexec] = "1"
do_compile[noexec] = "1"

FW_INSTALL_DIR = "${D}/lib/firmware"
FW_INSTALL_DTE_NAME ?= "dte.bin"
FW_INSTALL_FRPE_NAME ?= "frpe.bin"
FW_INSTALL_PPE-TX_NAME ?= "ppe_tx.bin"
FW_INSTALL_PPE-RX_NAME ?= "ppe_rx.bin"
LLCE_LOCAL_FIRMWARE_DIR ?= "."

SRC_URI = " \
	file://${LLCE_LOCAL_FIRMWARE_DIR}/${FW_INSTALL_DTE_NAME} \
	file://${LLCE_LOCAL_FIRMWARE_DIR}/${FW_INSTALL_FRPE_NAME} \
	file://${LLCE_LOCAL_FIRMWARE_DIR}/${FW_INSTALL_PPE-TX_NAME} \
	file://${LLCE_LOCAL_FIRMWARE_DIR}/${FW_INSTALL_PPE-RX_NAME} \
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

do_install() {

        echo  ${LLCE_LOCAL_FIRMWARE_DIR}/${FW_INSTALL_DTE_NAME}
        if [ -f ${LLCE_LOCAL_FIRMWARE_DIR}/${FW_INSTALL_DTE_NAME} ];then
                mkdir -p "${FW_INSTALL_DIR}"
                install -D "${LLCE_LOCAL_FIRMWARE_DIR}/${FW_INSTALL_DTE_NAME}" "${FW_INSTALL_DIR}/${FW_INSTALL_DTE_NAME}"
        fi

        echo ${LLCE_LOCAL_FIRMWARE_DIR}/${FW_INSTALL_FRPE_NAME}
        if [ -f ${LLCE_LOCAL_FIRMWARE_DIR}/${FW_INSTALL_FRPE_NAME} ];then
                mkdir -p "${FW_INSTALL_DIR}"
                install -D "${LLCE_LOCAL_FIRMWARE_DIR}/${FW_INSTALL_FRPE_NAME}" "${FW_INSTALL_DIR}/${FW_INSTALL_FRPE_NAME}"
        fi

        echo ${LLCE_LOCAL_FIRMWARE_DIR}/${FW_INSTALL_PPE-TX_NAME}
        if [ -f ${LLCE_LOCAL_FIRMWARE_DIR}/${FW_INSTALL_PPE-TX_NAME} ];then
                mkdir -p "${FW_INSTALL_DIR}"
                install -D "${LLCE_LOCAL_FIRMWARE_DIR}/${FW_INSTALL_PPE-TX_NAME}" "${FW_INSTALL_DIR}/${FW_INSTALL_PPE-TX_NAME}"
        fi

        echo ${LLCE_LOCAL_FIRMWARE_DIR}/${FW_INSTALL_PPE-RX_NAME}
        if [ -f ${LLCE_LOCAL_FIRMWARE_DIR}/${FW_INSTALL_PPE-RX_NAME} ];then
                mkdir -p "${FW_INSTALL_DIR}"
                install -D "${LLCE_LOCAL_FIRMWARE_DIR}/${FW_INSTALL_PPE-RX_NAME}" "${FW_INSTALL_DIR}/${FW_INSTALL_PPE-RX_NAME}"
        fi
}

do_deploy() {

        install -d ${DEPLOYDIR}

        if [ -f ${FW_INSTALL_DIR}/${FW_INSTALL_DTE_NAME} ];then
                install -m 0644 ${FW_INSTALL_DIR}/${FW_INSTALL_DTE_NAME} ${DEPLOYDIR}/${FW_INSTALL_DTE_NAME}
        fi

        if [ -f ${FW_INSTALL_DIR}/${FW_INSTALL_FRPE_NAME} ];then
                install -m 0644 ${FW_INSTALL_DIR}/${FW_INSTALL_FRPE_NAME} ${DEPLOYDIR}/${FW_INSTALL_FRPE_NAME}
        fi

	if [ -f ${FW_INSTALL_DIR}/${FW_INSTALL_PPE-TX_NAME} ];then
                install -m 0644 ${FW_INSTALL_DIR}/${FW_INSTALL_PPE-TX_NAME} ${DEPLOYDIR}/${FW_INSTALL_PPE-TX_NAME}
        fi

	if [ -f ${FW_INSTALL_DIR}/${FW_INSTALL_PPE-RX_NAME} ];then
                install -m 0644 ${FW_INSTALL_DIR}/${FW_INSTALL_PPE-RX_NAME} ${DEPLOYDIR}/${FW_INSTALL_PPE-RX_NAME}
        fi

}

addtask do_deploy after do_install

# do_package_qa throws error "QA Issue: Architecture did not match"
# when checking the firmware
do_package_qa[noexec] = "1"
do_package_qa_setscene[noexec] = "1"

FILES_${PN} += "/lib/firmware/${FW_INSTALL_DTE_NAME}"
FILES_${PN} += "/lib/firmware/${FW_INSTALL_FRPE_NAME}"
FILES_${PN} += "/lib/firmware/${FW_INSTALL_PPE-TX_NAME}"
FILES_${PN} += "/lib/firmware/${FW_INSTALL_PPE-RX_NAME}"

COMPATIBLE_MACHINE_nxp-s32g2xx = "nxp-s32g2xx"
