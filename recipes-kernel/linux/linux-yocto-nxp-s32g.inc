#
# Copyright (C) 2020 Wind River Systems, Inc.
#

COMPATIBLE_MACHINE_nxp-s32g = "nxp-s32g"
TARGET_SUPPORTED_KTYPES_nxp-s32g = "standard preempt-rt"

KERNEL_VERSION_SANITY_SKIP = "1"
SRCREV_machine_nxp-s32g ?= "${AUTOREV}"
SRCREV_meta_nxp-s32g ?= "${AUTOREV}"
