
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append_nxp-s32g = " \
	file://0001-rngd-jitter-drop-affinity-settings-for-entropy-task-.patch \
"
