# Copyright 2019-2021 NXP
#
# This is the PFE driver for Linux kernel 5.4 and 5.10

SUMMARY = "Linux driver for the Packet Forwarding Engine hardware"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE-GPL2.txt;md5=5dcdfe25f21119aa5435eab9d0256af7"

inherit module deploy

# Dummy entry to keep the recipe parser happy if we don't use this recipe
PFE_LOCAL_FIRMWARE_DIR ?= "."
FW_INSTALL_DIR = "${D}/lib/firmware"
FW_INSTALL_CLASS_NAME ?= "s32g_pfe_class.fw"
FW_INSTALL_UTIL_NAME ?= "s32g_pfe_util.fw"

SRC_URI = "git://source.codeaurora.org/external/autobsps32/extra/pfeng;protocol=https;branch=master \
	file://0001-pfe_compiler-add-GCC-version-10.2.0-support.patch \
	file://0001-fix-hwts-kmemleak.patch \
        file://0002-fix-hardware-feature-kmemleak.patch \
	file://${PFE_LOCAL_FIRMWARE_DIR} \
	${@bb.utils.contains('PREFERRED_PROVIDER_virtual/kernel', 'linux-yocto-rt', 'file://0001-pfe-pfeng-bman-enable-preempt-when-hif-channle-fill-.patch', '', d)} \
	${@bb.utils.contains('PREFERRED_PROVIDER_virtual/kernel', 'linux-yocto-rt', 'file://0002-pfe-sw-move-mutex-lock-below-of-destroy-the-workqueu.patch', '', d)} \
	"
SRCREV = "84dabb46ecce1d8d1f8a8ddb50051a3719568727"

PATCHTOOL = "git"

# Tell yocto not to bother stripping our binaries, especially the firmware
# since 'aarch64-fsl-linux-strip' fails with error code 1 when parsing the firmware
# ("Unable to recognise the format of the input file")
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_SYSROOT_STRIP = "1"

S = "${WORKDIR}/git"
MDIR = "${S}/sw/linux-pfeng"
INSTALL_DIR = "${D}/${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/ethernet/nxp/pfe"

EXTRA_OEMAKE:append = " KERNELDIR=${STAGING_KERNEL_DIR} MDIR=${MDIR} -C ${MDIR} V=1 drv-build"

# Build PFE for both 1.1 and 2.0 SoC revision
# The user can choose to build specific version only by overwriting this variable
# in this file or in conf/local.conf
# For example, to build only for Rev 2.0, set PFE_SUPPORTED_REV = "2.0"
PFE_SUPPORTED_REV ?= "2.0"

module_do_compile() {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS

	for rev in ${PFE_SUPPORTED_REV}; do

		# convert SoC revision to PFE revision
		if [ "${rev}" = "1.1" ]; then
			pfe_rev="PFE_CFG_IP_VERSION_NPU_7_14"
		elif [ "${rev}" = "2.0" ]; then
			pfe_rev="PFE_CFG_IP_VERSION_NPU_7_14a"
		else
			bberror "Cannont convert '${rev}' to a PFE revision"
		fi

		# standard module build, but setting PFE_CFG_IP_VERSION
		oe_runmake PFE_CFG_IP_VERSION="${pfe_rev}" \
		KERNEL_PATH=${STAGING_KERNEL_DIR}   \
		KERNEL_VERSION=${KERNEL_VERSION}    \
		CC="${KERNEL_CC}" LD="${KERNEL_LD}" \
		AR="${KERNEL_AR}" \
		O=${STAGING_KERNEL_BUILDDIR} \
		KBUILD_EXTRA_SYMBOLS="${KBUILD_EXTRA_SYMBOLS}" \
		${MAKE_TARGETS}

		# all revisions are installed, set them with revision suffix
		mv "${MDIR}/pfeng.ko" "${MDIR}/pfeng-${rev}.ko"
	done
}

module_do_install() {
	# install all supported revisions
	for rev in ${PFE_SUPPORTED_REV}; do
		install -D "${MDIR}/pfeng-${rev}.ko" "${INSTALL_DIR}/pfeng-${rev}.ko"
	done

	if [ -f ${WORKDIR}/${PFE_LOCAL_FIRMWARE_DIR}/${FW_INSTALL_CLASS_NAME} ];then
		mkdir -p "${FW_INSTALL_DIR}"
		install -D "${WORKDIR}/${PFE_LOCAL_FIRMWARE_DIR}/${FW_INSTALL_CLASS_NAME}" "${FW_INSTALL_DIR}/${FW_INSTALL_CLASS_NAME}"
	fi

	if [ -f ${WORKDIR}/${PFE_LOCAL_FIRMWARE_DIR}/${FW_INSTALL_UTIL_NAME} ];then
		mkdir -p "${FW_INSTALL_DIR}"
		install -D "${WORKDIR}/${PFE_LOCAL_FIRMWARE_DIR}/${FW_INSTALL_UTIL_NAME}" "${FW_INSTALL_DIR}/${FW_INSTALL_UTIL_NAME}"
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

FILES:${PN} += "/lib/firmware/${FW_INSTALL_CLASS_NAME} \
    /lib/firmware/${FW_INSTALL_UTIL_NAME} \
    ${sysconfdir}/modules-load.d/* \
"


COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:nxp-s32g = "nxp-s32g"
