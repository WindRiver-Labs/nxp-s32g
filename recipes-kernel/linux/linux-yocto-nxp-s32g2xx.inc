#
# Copyright (C) 2020 Wind River Systems, Inc.
#

COMPATIBLE_MACHINE_nxp-s32g2xx = "nxp-s32g2xx"
TARGET_SUPPORTED_KTYPES_nxp-s32g2xx = "standard preempt-rt"

KERNEL_VERSION_SANITY_SKIP = "1"
SRCREV_machine_nxp-s32g2xx ?= "${AUTOREV}"
SRCREV_meta_nxp-s32g2xx ?= "${AUTOREV}"
