DESCRIPTION = "NXP IPCF FIRMWARES"
PROVIDES += "llce"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LIENCES.txt;md5=324d5bf9404f8bf2b01cfc66037447f6"

inherit deploy

do_configure[noexec] = "1"
do_compile[noexec] = "1"

LLCE_LOCAL_FIRMWARE_DIR_DTE_BIN ?= "."
LLCE_LOCAL_FIRMWARE_DIR_FRPE_BIN ?= "."
LLCE_LOCAL_FIRMWARE_DIR_PPE-TX_BIN ?= "."
LLCE_LOCAL_FIRMWARE_DIR_PPE-RX_BIN ?= "."

SRC_URI = " \
	file://${LLCE_LOCAL_FIRMWARE_DIR_DTE_BIN} \
	file://${LLCE_LOCAL_FIRMWARE_DIR_FRPE_BIN} \
	file://${LLCE_LOCAL_FIRMWARE_DIR_PPE-TX_BIN} \
	file://${LLCE_LOCAL_FIRMWARE_DIR_PPE-RX_BIN} \
	file://LIENCES.txt \
"

# Tell yocto not to bother stripping our binaries, especially the firmware
# since 'aarch64-fsl-linux-strip' fails with error code 1 when parsing the firmware
# ("Unable to recognise the format of the input file")
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_SYSROOT_STRIP = "1"

S = "${WORKDIR}"

FW_INSTALL_DIR = "${D}/lib/firmware"
FW_INSTALL_DTE_NAME ?= "dte.bin"
FW_INSTALL_FRPE_NAME ?= "frpe.bin"
FW_INSTALL_PPE-TX_NAME ?= "ppe_tx.bin"
FW_INSTALL_PPE-RX_NAME ?= "ppe_rx.bin"


PACKAGE_ARCH = "${MACHINE_ARCH}"

do_install() {

	echo  ${LLCE_LOCAL_FIRMWARE_DIR_DTE_BIN}
        if [ -f ${LLCE_LOCAL_FIRMWARE_DIR_DTE_BIN} ];then
                mkdir -p "${FW_INSTALL_DIR}"
                install -D "${LLCE_LOCAL_FIRMWARE_DIR_DTE_BIN}" "${FW_INSTALL_DIR}/${FW_INSTALL_DTE_NAME}"
        fi

	echo ${LLCE_LOCAL_FIRMWARE_DIR_FRPE_BIN}
        if [ -f ${LLCE_LOCAL_FIRMWARE_DIR_FRPE_BIN} ];then
                mkdir -p "${FW_INSTALL_DIR}"
                install -D "${LLCE_LOCAL_FIRMWARE_DIR_FRPE_BIN}" "${FW_INSTALL_DIR}/${FW_INSTALL_FRPE_NAME}"
        fi

	echo ${LLCE_LOCAL_FIRMWARE_DIR_PPE-TX_BIN}
        if [ -f ${LLCE_LOCAL_FIRMWARE_DIR_PPE-TX_BIN} ];then
                mkdir -p "${FW_INSTALL_DIR}"
                install -D "${LLCE_LOCAL_FIRMWARE_DIR_PPE-TX_BIN}" "${FW_INSTALL_DIR}/${FW_INSTALL_PPE-TX_NAME}"
        fi

	echo ${LLCE_LOCAL_FIRMWARE_DIR_PPE-RX_BIN}
        if [ -f ${LLCE_LOCAL_FIRMWARE_DIR_PPE-RX_BIN} ];then
                mkdir -p "${FW_INSTALL_DIR}"
                install -D "${LLCE_LOCAL_FIRMWARE_DIR_PPE-RX_BIN}" "${FW_INSTALL_DIR}/${FW_INSTALL_PPE-RX_NAME}"
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
