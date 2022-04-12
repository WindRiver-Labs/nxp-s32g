# SPDX-License-Identifier:	BSD-3-Clause
#
# Copyright 2018-2019 NXP
#

SUMMARY = "Support for Inter-Process(or) Communication over Shared Memory (ipc-shm)"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

inherit module deploy

URL ?= "git://source.codeaurora.org/external/autobsps32/ipcf/ipc-shm;protocol=https"
BRANCH ?= "release/bsp32.0"
SRC_URI = "${URL};branch=${BRANCH}"
SRCREV = "79164a0b055c759557a00f2ce3ec73a90361937c"


S = "${WORKDIR}/git"
DESTDIR="${D}"
IPCF_MDIR = "${S}"
IPCF_SAMPLE_MDIR = "${S}/sample"
INSTALL_DIR = "${D}/${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/ipc-shm"
EXTRA_OEMAKE_append = " -C ./sample INSTALL_DIR=${DESTDIR} KERNELDIR=${KBUILD_OUTPUT} "
MODULES_MODULE_SYMVERS_LOCATION = "."

IPCF_MOD_DEV_NAME = "ipc-shm-dev.ko"
IPCF_MOD_SAMPLE_NAME = "ipc-shm-sample.ko"
IPCF_MOD_UIO_NAME = "ipc-shm-uio.ko"

PROVIDES += "kernel-module-ipc-shm-sample"
RPROVIDES_${PN} += "kernel-module-ipc-shm-sample"
PROVIDES += "kernel-module-ipc-shm-dev"
RPROVIDES_${PN} += "kernel-module-ipc-shm-dev"
PROVIDES += "kernel-module-ipc-shm-uio"
RPROVIDES_${PN} += "kernel-module-ipc-shm-uio"

export SUPPORTED_PLATS="s32g2 s32g3"

# Prevent to load ipc-shm-uio at boot time
KERNEL_MODULE_PROBECONF += "ipc-shm-uio"
module_conf_ipc-shm-uio = "blacklist ipc-shm-uio"

# install ipcf modules
do_compile() {
    for plat in ${SUPPORTED_PLATS}; do
        export PLATFORM_FLAVOR=${plat}
        module_do_compile
        for m in ipc-shm-dev.ko sample/ipc-shm-sample.ko ipc-shm-uio.ko; do
            cp $m $m.$plat
        done
    done
}

do_install() {

    mkdir -p ${INSTALL_DIR}

    for plat in "" `echo $SUPPORTED_PLATS | sed 's/\</./g'`; do
        install -D ${IPCF_MDIR}/${IPCF_MOD_DEV_NAME}$plat ${INSTALL_DIR}/
        install -D ${IPCF_MDIR}/${IPCF_MOD_UIO_NAME}$plat ${INSTALL_DIR}/
        install -D ${IPCF_SAMPLE_MDIR}/${IPCF_MOD_SAMPLE_NAME}$plat ${INSTALL_DIR}/
    done
}

do_deploy() {
	install -d ${DEPLOYDIR}

	bins="${IPCF_M7_APP_BIN_NAME} ${IPCF_M7_APP_BIN_NAME_S32G3}" 
	for bin in $bins; do
		if [ -f ${IPCF_M7_APP_BIN_DIR}/${bin} ];then
			install -m 0644  ${IPCF_M7_APP_BIN_DIR}/${bin} ${DEPLOYDIR}/${bin}
		fi
	done
}
addtask do_deploy after do_install

pkg_postinst_ontarget:${PN} () {

    module_dir="/lib/modules/`uname -r`/kernel/drivers/ipc-shm"
    mods="ipc-shm-dev ipc-shm-sample ipc-shm-uio"

    [ -d $module_dir ]  || return

    cd ${module_dir}
    for plat in ${SUPPORTED_PLATS}; do
        if grep -q $plat /sys/firmware/devicetree/base/compatible; then
            for m in $mods; do
                mv -f $m.ko.$plat $m.ko
            done
        else
            rm `echo $mods | sed "s/[^ ]*/&.ko.$plat/g"`
        fi
    done

    depmod

}

FILES:${PN} += "${sysconfdir}/modprobe.d/* ${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/ipc-shm/*.s32g*"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE_nxp-s32g = "nxp-s32g"
