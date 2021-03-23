# Copyright 2019-2020 NXP
#
# This is the PFE driver for Linux kernel 4.19 and 5.4

SUMMARY = "Linux driver for the Packet Forwarding Engine hardware"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE-GPL2.txt;md5=5dcdfe25f21119aa5435eab9d0256af7"

inherit module deploy

# Dummy entry to keep the recipe parser happy if we don't use this recipe
PFE_LOCAL_FIRMWARE_DIR_CLASS_BIN ?= "."
PFE_LOCAL_FIRMWARE_DIR_UTIL_BIN ?= "."

SRC_URI = "git://source.codeaurora.org/external/autobsps32/extra/pfeng;protocol=https \
	file://0001-pfe_compiler-add-GCC-version-10.2.0-support.patch \
	file://0001-pfe-oal-modify-the-GFP-flag-to-GFP_ATOMIC-for-kzallo.patch \
	file://${PFE_LOCAL_FIRMWARE_DIR_CLASS_BIN} \
	file://${PFE_LOCAL_FIRMWARE_DIR_UTIL_BIN} \
	file://0001-driver-pfe-replace-ioremap_nocache-with-ioremap.patch \
	file://0002-driver-pfe-adjust-parameters-for-interface-of_get_ph.patch \
	file://0003-driver-pfe-use-the-latest-callback-define-of-struct-.patch \
	"
SRCREV = "9ddaffd9a37d85e0fc3e08ecb440484212c66e58"

# Tell yocto not to bother stripping our binaries, especially the firmware
# since 'aarch64-fsl-linux-strip' fails with error code 1 when parsing the firmware
# ("Unable to recognise the format of the input file")
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_SYSROOT_STRIP = "1"

S = "${WORKDIR}/git"
MDIR = "${S}/sw/linux-pfeng"
INSTALL_DIR = "${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/net/ethernet/nxp/pfe"
FW_INSTALL_DIR = "${D}/lib/firmware"
FW_INSTALL_CLASS_NAME ?= "s32g_pfe_class.fw"
FW_INSTALL_UTIL_NAME ?= "s32g_pfe_util.fw"

EXTRA_OEMAKE_append = " KBUILD_MODPOST_WARN=0 KERNELDIR=${STAGING_KERNEL_DIR} MDIR=${MDIR} -C ${MDIR} V=1 all"

module_do_install() {
	install -D ${MDIR}/pfeng.ko ${INSTALL_DIR}/pfeng.ko
	
	if [ -f ${WORKDIR}/${PFE_LOCAL_FIRMWARE_DIR_CLASS_BIN} ];then
		mkdir -p "${FW_INSTALL_DIR}"
		install -D "${WORKDIR}/${PFE_LOCAL_FIRMWARE_DIR_CLASS_BIN}" "${FW_INSTALL_DIR}/${FW_INSTALL_CLASS_NAME}"
	fi

	if [ -f ${WORKDIR}/${PFE_LOCAL_FIRMWARE_DIR_UTIL_BIN} ];then
		mkdir -p "${FW_INSTALL_DIR}"
		install -D "${WORKDIR}/${PFE_LOCAL_FIRMWARE_DIR_UTIL_BIN}" "${FW_INSTALL_DIR}/${FW_INSTALL_UTIL_NAME}"
	fi
}

do_deploy() {
	install -d ${DEPLOYDIR}

	if [ -f ${FW_INSTALL_DIR}/${FW_INSTALL_CLASS_NAME} ];then
		install -m 0644 ${FW_INSTALL_DIR}/${FW_INSTALL_CLASS_NAME} ${DEPLOYDIR}/${FW_INSTALL_CLASS_NAME}
	fi

	if [ -f ${FW_INSTALL_DIR}/${FW_INSTALL_UTIL_NAME} ];then
		install -m 0644 ${FW_INSTALL_DIR}/${FW_INSTALL_UTIL_NAME} ${DEPLOYDIR}/${FW_INSTALL_UTIL_NAME}
	fi

}

addtask do_deploy after do_install

# do_package_qa throws error "QA Issue: Architecture did not match"
# when checking the firmware
do_package_qa[noexec] = "1"
do_package_qa_setscene[noexec] = "1"

FILES_${PN} += "/lib/modules/* \
    /lib/firmware/${FW_INSTALL_CLASS_NAME} \
    /lib/firmware/${FW_INSTALL_UTIL_NAME} \
    ${base_libdir}/* \
    ${sysconfdir}/modules-load.d/* \
"

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE_nxp-s32g2xx = "nxp-s32g2xx"
