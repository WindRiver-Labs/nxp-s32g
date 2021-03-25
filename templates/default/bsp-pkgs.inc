IMAGE_INSTALL += "can-utils"
IMAGE_INSTALL += "ptpd"
IMAGE_INSTALL += "u-boot-s32"
IMAGE_INSTALL += "libp11"
IMAGE_INSTALL += "pkcs11-hse"
IMAGE_INSTALL += "pkcs11-hse-examples"

NXP_S32G2XX_WIC_INC ?= ""
NXP_S32G2XX_WIC_INC_nxp-s32g2xx = "make_nxp_s32g2xx_wic.inc"
require conf/${NXP_S32G2XX_WIC_INC}
