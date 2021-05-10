# SPDX-License-Identifier:	BSD-3-Clause
#
# Copyright 2018-2019 NXP
#

SUMMARY = "Support for Inter-Process(or) Communication over Shared Memory (ipc-shm)"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

inherit module deploy

URL ?= "git://source.codeaurora.org/external/autobsps32/ipcf/ipc-shm;protocol=https"
BRANCH ?= "release/SW32G2_IPCF_2.1.1_D2103"
SRC_URI = "${URL};branch=${BRANCH}"
SRCREV = "c70c0208ec295186eda80608f08994f2f8e5bcb5"

SRC_URI += " \
	file://0001-ipc-shm-os-modify-tasklet-declaration-to-compatible-.patch \
"

S = "${WORKDIR}/git"
DESTDIR="${D}"
IPCF_MDIR = "${S}"
IPCF_SAMPLE_MDIR = "${S}/sample"
INSTALL_DIR = "${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/ipc-shm"
EXTRA_OEMAKE_append = " -C ./sample INSTALL_DIR=${DESTDIR} KERNELDIR=${KBUILD_OUTPUT} "
MODULES_MODULE_SYMVERS_LOCATION = "."

IPCF_MOD_DEV_NAME = "ipc-shm-dev.ko"
IPCF_MOD_SAMPLE_NAME = "ipc-shm-sample.ko"
IPCF_MOD_UIO_NAME = "ipc-shm-uio.ko"

IPCF_M7_APP_BIN_DIR ?= "."
IPCF_M7_APP_BIN_NAME ?= "IPCF_Example_S32G274.bin"

PROVIDES += "kernel-module-ipc-shm-sample"
RPROVIDES_${PN} += "kernel-module-ipc-shm-sample"
PROVIDES += "kernel-module-ipc-shm-dev"
RPROVIDES_${PN} += "kernel-module-ipc-shm-dev"
PROVIDES += "kernel-module-ipc-shm-uio"
RPROVIDES_${PN} += "kernel-module-ipc-shm-uio"

# Prevent to load ipc-shm-uio at boot time
KERNEL_MODULE_PROBECONF += "ipc-shm-uio"
module_conf_ipc-shm-uio = "blacklist ipc-shm-uio"

# install ipcf modules
module_do_install() {

	mkdir -p ${INSTALL_DIR}

        install -D ${IPCF_MDIR}/${IPCF_MOD_DEV_NAME} ${INSTALL_DIR}/
        install -D ${IPCF_MDIR}/${IPCF_MOD_UIO_NAME} ${INSTALL_DIR}/
        install -D ${IPCF_SAMPLE_MDIR}/${IPCF_MOD_SAMPLE_NAME} ${INSTALL_DIR}/
}

do_deploy() {
	install -d ${DEPLOYDIR}

	if [ -f ${IPCF_M7_APP_BIN_DIR}/${IPCF_M7_APP_BIN_NAME} ];then
		install -m 0644  ${IPCF_M7_APP_BIN_DIR}/${IPCF_M7_APP_BIN_NAME} ${DEPLOYDIR}/${IPCF_M7_APP_BIN_NAME}
	fi
}
addtask do_deploy after do_install

FILES_${PN} += "${base_libdir}/*"
FILES_${PN} += "${sysconfdir}/modprobe.d/*"
