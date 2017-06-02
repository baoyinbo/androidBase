/**
 * 文 件 名:  HiMsgCdConstants.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/7/23
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.gxb.gxbcompanyintegrity.net;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author 江钰锋 00501
 * @version [版本号, 16/7/23]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HiMsgCdConstants {
    public static final String SUCCESS = "00000";
    /** SYS_FAIL,系统错误 */
    public static final String SYS_FAIL = "99999";
    /** TX_AUTH_FAIL,接口不存在 */
    public static final String TX_AUTH_FAIL = "T0000";

    /*** 密文长度不对 ****/
    public static final String TX_MSGLENGTHTOOSHOT_FAIL = "T0001";
    /*** 签名验证错误 ****/
    public static final String TX_SIGNAUTH_FAIL = "T0002";
    /*** 渠道检查错误 ****/
    public static final String TX_CHANNELAUTH_FAIL = "T0003";

    /** demo添加NULL值 */
    public static final String DEMO_ADD_NULL = "D0000";
    /** demo添加NULL值 */
    public static final String DEMO_ADD_FAIL = "D0100";
    /** demo添加NULL值 */
    public static final String DEMO_QRY_NULL = "D0300";
    /** 查询订单失败 **/
    public static final String ORDER_QRY_NULL = "P0001";
    /** 查询产品失败 */
    public static final String PRODUCR_QRY_NULL = "P0002";
    /** 查询机构信息失败 */
    public static final String ORG_QRY_NULL = "P0003";
    /**
     * 获取理财产品列表失败
     */
    public static final String PRD_LIST_QRY_NULL = "P0004";
    /**
     * 获取理财产品信息失败
     */
    public static final String PRD_SINGLE_QRY_NULL = "P0005";
    /**
     * 获取理财机构合作银行信息失败
     */
    public static final String ORGBANK_LIST_QRY_NULL = "P0006";
    /**
     * 该产品暂未开放购买
     */
    public static final String PRD_PURC_BEG_DATE = "P0007";

    /**
     * 该产品购买已过期
     */
    public static final String PRD_PURC_END_DATE = "P0008";
    /**
     * 申购金额超出单笔申购上限金额
     */
    public static final String PRD_PURC_MAX_CNT = "P0009";
    /**
     * 申购金额低于单笔申购下限金额
     */
    public static final String PRD_PURC_MIN_CNT = "P0010";
    /**
     * 累计申购金额高于指定周期申购上限金额
     */
    public static final String PRD_PERD_MAX_CNT = "P0011";
    /**
     * 下单失败
     */
    public static final String PRD_PURC_ORD_ADD = "P0012";
    /**
     * 注册微理财帐号失败
     */
    public static final String PRD_USR_REGIST = "P0013";
    /**
     * 支付失败
     */
    public static final String PRD_PURC_ORDER = "P0014";
    /**
     * 赎回金额非法
     */
    public static final String PRD_REDP_AMOUNT = "P0015";
    /**
     * 该用户未投产品
     */
    public static final String PRD_INF_NULL = "P0016";
    /**
     * 订单查询失败
     */
    public static final String PRD_PURC_ORDNO = "P0017";
    /**
     * 该理财产品暂未到期，不能赎回
     */
    public static final String PRD_REDP_DATE_CHECK = "P0018";

    /**
     * 下单失败:注册微理财账号信息失败
     */
    public static final String PRD_REGIST_USRINFO = "P0019";
    /**
     * 获取微理财账号信息失败
     */
    public static final String PRD_QRY_USRINFO = "P0020";
    /**
     * 赎回失败
     */
    public static final String PRD_REDP_ORDER = "P0021";
    /**
     * 查询兑付金额失败
     */
    public static final String PRD_REPAY_DETAIL = "P0022";
    /**
     * 订单查询失败
     */
    public static final String PRD_GET_INVEST = "P0023";
    /**
     * 赎回金额与申购金额不一致
     */
    public static final String PRD_REDP_ORDAMT = "P0024";
    /**
     * 申购金额需要按份额倍数够买
     */
    public static final String PRD_ORDAMT_TIMES = "P0025";

    /**
     * 查询卡bin失败
     */
    public static final String BANKCARDBIN_QRY_NULL = "P0026";
    /**
     * 接口请求报文异常
     */
    public static final String PRD_CHKREQ_ERR = "P0027";
    /**
     * 更新用户理财账号信息失败
     */
    public static final String PRD_USR_UPDATE = "P0028";
    /**
     * 同步用户银行卡信息失败
     */
    public static final String PRD_USRBNK_SYNC = "P0029";
    /**
     * 插入用户资产明细记录失败
     */
    public static final String PRD_INSERTASSDET_FAILD = "P0030";
    /**
     * 插入用户收益记录失败
     */
    public static final String PRD_INSERTERNDET_FAILD = "P0031";
    /**
     * 更新申购订单信息失败
     */
    public static final String PRD_PURC_UPDATE = "P0032";
    /**
     * 更新产品信息失败
     */
    public static final String PRD_INF_UPDATE = "P0033";
    /**
     * 更新赎回订单信息失败
     */
    public static final String PRD_REDP_UPDATE = "P0034";
    /**
     * 更新用户资产明细记录失败
     */
    public static final String PRD_UPDATEASSDET_FAILD = "P0035";
    /**
     * 更新用户收益记录失败
     */
    public static final String PRD_UPDATEERNDET_FAILD = "P0036";
    /**
     * 插入用户历史收益记录失败
     */
    public static final String PRD_INSERTERNHST_FAILD = "P0037";
    /**
     * 支付流水状态为S,D，提示订单已提交
     */
    public static final String PRD_ON_EXIST="P0278";
    /**
     * 该产品购买已过期
     */
    public static final String PRD_STS_OUTDATE = "P0038";
    /**
     * 该产品暂停购买
     */
    public static final String PRD_STS_PAUSE = "P0039";
    /**
     * 更新产品状态失败
     */
    public static final String PRD_STS_UPDATE = "P0040";
    /**
     * 申购金额需要按份额倍数够买0.01
     */
    public static final String PRD_ORDAMT_TIMES_NEW = "P0042";
    /**
     * 亿百润产品赎回支行提示
     */
    public static final String YBR_PRD_REDEEM = "P0044";
    /**
     * 产品注册渠道限制
     */
    public static final String PRD_REG_CNL = "P0045";

    /**
     * 短信验证异常
     */
    public static final String URM_SMSCHK_EXCEPTION = "U0001";

    /**
     * 用户信息已存在，无需重复注册
     */
    public static final String URM_CHKUSR_EXIST = "U0002";

    /**
     * 短信校验失败
     */
    public static final String URM_SMSCHK_FAIL = "U0003";

    /**
     * 客户端授权信息错误
     */
    public static final String URM_CLICHK_FAIL = "U0004";

    /**
     * 用户注册失败
     */
    public static final String URM_USRREG_FAIL = "U0005";

    /**
     * 用户登录信息缺失
     */
    public static final String URM_USRLOG_LESS = "U0006";

    /**
     * 用户身份信息校验失败
     */
    public static final String URM_CHKUSR_NOMATCH = "U0007";

    /**
     * 登录密码校验失败
     */
    public static final String URM_CHKPWD_FAIL = "U0008";

    /**
     * 用户信息不存在
     */
    public static final String URM_CHKUSR_NOEXIST = "U0009";

    /**
     * 短信类型有误
     */
    public static final String URM_SMS_WRONGT_TYP = "U0010";

    /**
     * 用户已绑定身份证号码
     */
    public static final String URM_IDCARD_EXIST = "U0011";

    /**
     * 身份证号校验失败
     */
    public static final String URM_CHKIDCARD_FAIL = "U0012";

    /**
     * 用户未登录
     */
    public static final String URM_CHKLOGIN_FAIL = "U0013";

    /**
     * 密码设置无效
     */

    /**
     * 密码设置过于简单，请重新设置
     */
    public static final String URM_SETPWD_SIMP = "U0015";

    /**
     * 手机号码不能为空
     */public static final String URM_SETPWD_FAIL = "U0014";

    public static final String URM_CHKMBL_NOTEXIST = "U0016";

    /**
     * 接口请求报文异常
     */
    public static final String URM_CHKREQ_ERR = "U0017";

    /**
     * 短信发送失败
     */
    public static final String URM_SMS_SENDFAIL = "U0018";

    /**
     * 银行卡信息无效
     */
    public static final String URM_BNKCRD_FAIT = "U0019";
    /**
     * 支付密码校验失败
     */
    public static final String PAY_CHKPWD_FAIL = "U0020";

    /**
     * 身份证号已存在
     */
    public static final String URM_CHKIDCARD_EXIST = "U0021";

    /**
     * 银行卡已被绑定
     */
    public static final String URM_BNKCRD_EXIST = "U0022";

    /**
     * 登录密码和交易密码不可相同
     */
    public static final String URM_USRCERT_SAME = "U0023";

    /**
     * 当日获取短信数量已达上限
     */
    public static final String URM_USRSMS_MAXCNT = "U0024";

    /**
     * 产品内测阶段，暂未开放注册
     */
    public static final String URM_REDLST_FAIL = "U0025";
    /**
     * 您输入的号码未注册
     */
    public static final String URM_CHKUSR_ISEXIST = "U0026";

    /**
     * 图形验证码校验失败
     */
    public static final String URM_CHKUSR_CODE = "U0028";

    /**
     * 支付密码校验错误
     */
    public static final String PAY_PWD_CHKPWD_ERROR = "U0036";

    /**
     * 支付密码锁定
     */
    public static final String PAY_PWD_CLOCK = "U0040";
    /**
     * 单点登录
     */
    public static final String SINGLE_LOGIN = "U0055";

    /**
     * 检查客户端版本不在支持列表中
     */
    public static final String CAS_CHKCHANNEL_NOMATCH = "C0001";

    /**
     * 未配置首页产品
     */
    public static final String CAS_INDEXPROD_NOTEXIST = "C0002";

    /**
     * 未配置闪屏页
     */
    public static final String CAS_FLASH_NOTEXIST = "C0003";

    /**
     * 未配置版本更新信息
     */
    public static final String CAS_VERSIONINFO_NOTEXIST = "C0004";

    /**
     * 图片上传失败异常
     */
    public static final String PIC_UPLOAD_EXCEPTION = "C0005";

    /**
     * 卡bin信息不存在
     */
    public static final String CMM_CARDBIN_NOTEXIST = "C0006";

    /**
     * 地区信息不存在
     */
    public static final String CMM_AREAINFO_NOTEXIST = "C0007";
    /**
     * 暂不支持信用卡
     */
    public static final String CMM_CARDBIN_CARD = "C0008";
    /**
     * 该机构暂不支持该银行卡
     */
    public static final String CMM_CARDBIN_ORGID = "C0009";

    /** ITF_CONNECT_FAIL,连接失败 */
    public static final String ITF_CONNECT_FAIL = "F0000";
    /** ITF_SEND_FAIL,发送请求失败 */
    public static final String ITF_SEND_FAIL = "F0001";
    /** ITF_CLIENT_NOT_EXIST,客户端不存在 */
    public static final String ITF_CLIENT_NOT_EXIST = "F1000";

    /**
     * 没有短信模板
     */
    public static final String SMS_TEMPLATE_NOTEXIST = "S0000";

    /**
     * 发送短信失败
     */
    public static final String SMS_SENDSMS_FAILD = "S0001";

    /**
     * 添加push信息失败
     */
    public static final String PUSH_INSERTPUSH_FAILD = "C0011";

    /**
     * jpush连接异常
     */
    public static final String PUSH_PUSHEXCEPTION_APICONNECTIONEXCEPTION = "C0012";

    /**
     * jpush请求异常
     */
    public static final String PUSH_PUSHEXCEPTION_APIREQUESTEXCEPTION = "C0013";

    /**
     * 插入收益历史记录错误
     */
    public static final String ERN_INSERTERNHIS_FAILD = "E0001";

    /**
     * 更新收益错误
     */
    public static final String ERN_UPDATEERN_FAILD = "E0002";

    /**
     * 无起息日期信息
     */
    public static final String ERN_NOAMTBEGDATE_FAILD = "E0003";

    /**
     * 玖富侧未兑付,暂时不能执行赎回操作
     */
    public static final String PRD_NOTOEXPDAY_FAILD = "P0041";
    /**
     * 亿百润侧未兑付,暂时不能执行赎回操作
     */
    public static final String PRD_YBRNOTOEXPDAY_FAILD = "P0043";
    /**
     * 亿百润侧未兑付,十点之后暂时不能执行赎回操作
     */
    public static final String PRD_YBRNOTOEXPDAY_FAILD_TIME = "P0046";
    /**
     * 亿百润侧未兑付,暂时不能执行续投操作
     */
    public static final String PRD_YBRNOTOEXPDAY_EXTION_FAILD = "P0047";
    /**
     * 亿百润侧未兑付,十点之后暂时不能执行续投操作
     */
    public static final String PRD_YBRNOTOEXPDAY_EXTION_FAILD_TIME = "P0048";
    /**
     /**
     * 亿百润用户申购未使用同一银行卡
     */
    public static final String PRD_NOTSAMECARD = "B0001";

    /**
     * 融云token
     */
    public static final String RCLOUD_TOKEN = "U0026";
    /**
     * 融云版本
     */
    public static final String RCLOUD_VERINFO = "C0010";

    public static final String URM_SMSNUM_PARSELIMIT = "U0027";

    /**
     * 非报表专用用户，不可登录，如有需要，请联系客服
     */
    public static final String MKM_USRREPT_NOTEXIST = "P0075";

    /**
     * 用户业务数据统计查询失败
     */
    public static final String MKM_USRBUSINESSDAT_FAILD = "P0076";
    /**
     * 产品续投失败
     */
    public static final String PRD_EXTENSION_FAILD = "P0078";
    /**
     * 查询YBR订单提现失败
     */
    public static final String ORD_CASH_DETAIL = "P0079";
    /**
     * 支付失败结果回传
     */
    public static final String ORD_PAY_FAILD_REASON = "P0080";
    /**
     * 银行卡注册失败
     */
    public static final String URM_BNKCRD_REGIST = "U0029";
    /**
     * 银行卡删除失败
     */
    public static final String URM_BNKCRD_DEL = "U0030";
    /**
     * 银行卡已被使用
     */
    public static final String URM_BNKCRD_USED= "U0031";
    /**
     * 请输入支行信息
     */
    public static final String INPUT_BRANCH_INFO  = "P0081";
    /**
     * 更新银行卡信息失败
     */
    public static final String UPDATE_BANK_INFO  = "P0082";
    /**
     * 订单已提现
     */
    public static final String ORD_REDEEM_INFO  = "P0083";
    /**
     * 请选择省市编码
     */
    public static final String ORD_REDEEM_CHECK_PROVCITY  = "P0084";

    /**
     * 订单已提现，不能重复提现
     */
    public static final String ORD_REDEEM_ISWRONG  = "P0109";

    public static final String JYQ_CITYCD_NULL = "P0110";
    /**
     * 该订单状态不支持续投功能
     */
    public static final String ORD_EXTENSION_STS= "P0111";
    /**
     * 必输项不能为空
     */
    public static final String NEED_SYND_ISNULL= "P0112";

    /**
     * 非常抱歉，此产品只有新用户才能购买
     */
    public static final String NEW_USER_BD_NO_BUY= "P0114";

    /**
     * 购买理财标的失败
     */
    public static final String BUY_FINANCE_FAILED = "P0116";

    /**
     *老用户， 已被推荐过，输入自己的口令：该聚口令不可使用
     */
    public static final String JYQ_KEY_USED= "U0032";

    /**
     * 数据库不存在：聚口令有误
     */
    public static final String JYQ_KEY_VALID= "U0033";
    /**
     *查询推荐奖励失败
     */
    public static final String QRY_RECOMMEND_EWARRD= "U0034";
    /**
     * 活动暂未开始
     */
    public static final String ACT_HAS_NOT_BEGIN= "U0035";
}
