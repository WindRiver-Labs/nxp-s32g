#
# Copyright (C) 2020 Wind River Systems, Inc.
#

UBOOT_SIGN_ENABLE = "1"
UBOOT_SIGN_KEYDIR = "${LAYER_PATH_s32g2xx-secure-layer}/s32g2xx-sample-key/fitImage-rsa2048-keys"
UBOOT_MKIMAGE_DTCOPTS = "-I dts -O dtb -p 2000"
UBOOT_SIGN_KEYNAME = "boot_key"

UBOOT_ENTRYPOINT = "0x80080000"
UBOOT_LOADADDRESS = "0x80080000"

UBOOT_MAKE_TARGET ?= "u-boot.s32 u-boot.dtb"
UBOOT_SUFFIX = "img"
PREFERRED_PROVIDER_u-boot = "u-boot-s32"
KERNEL_CLASSES  = " kernel-fitimage"
# So far the secure boot version is not updated yet and keeps the u-boot version with 2019.04
PREFERRED_VERSION_u-boot-s32 = "2019.04"

# NXPS32G secure boot feature supports rsa2048.
# Set KERNEL_RAS_TYPE as the expected value. The default one is rsa2048.
KERNEL_RAS_TYPE = "rsa2048"

# NXPS32G secure boot feature supports sha1 and sha256 digest.
# Set KERNEL_CSUM_TYPE as the expected value. The default one is sha1.
KERNEL_CSUM_TYPE = "sha1"
