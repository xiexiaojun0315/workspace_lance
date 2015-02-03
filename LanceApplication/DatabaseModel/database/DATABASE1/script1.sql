SET SQLBLANKLINES ON
CREATE TABLE COMPANY 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, NAME VARCHAR2(300 BYTE) 
, ENTERPRISE_PROPERTY VARCHAR2(32 BYTE) 
, EMPLOYEE_NUMBER_GRADE NUMBER 
, FOUND_YEAR DATE 
, ABOUT_COMPANY VARCHAR2(2100 BYTE) 
, SERVICE_DESC VARCHAR2(2100 BYTE) 
, VIDEO VARCHAR2(600 BYTE) 
, LOGO VARCHAR2(600 BYTE) 
, LOCATION_COUNTRY VARCHAR2(32 BYTE) 
, LOCATION_PROVINCE VARCHAR2(32 BYTE) 
, LOCATION_CITY VARCHAR2(32 BYTE) 
, LOCATION_DESC VARCHAR2(1200 BYTE) 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT CLIENT_COMPANY_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE CONTRACT 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, PROJECT_ID VARCHAR2(32 BYTE) 
, TITLE VARCHAR2(60 BYTE) 
, CONTENT CLOB 
, ATTACHMENT_LINK VARCHAR2(600) 
, CLIENT_NAME VARCHAR2(60 BYTE) 
, LANCER_NAME VARCHAR2(60 BYTE) 
, DATE_START DATE 
, DATE_END DATE 
, CLIENT_ROLE_TITLE VARCHAR2(120 BYTE) 
, LANCER_ROLE_TITLE VARCHAR2(120 BYTE) 
, POSTFORM VARCHAR2(20 BYTE) 
, HOURLY_PAY NUMBER 
, WEEKLY_HOURS NUMBER 
, FIXED_PAY_PRICE NUMBER 
, CLIENT_CONFIRM_DELAY_DAY NUMBER 
, CLIENT_PAY_DELAY_DAY NUMBER 
, FIXED_PAY_DATE NUMBER 
, FIXED_PAY_DELAY_HOLIDAY VARCHAR2(10 BYTE) 
, NEED_DAILY_REPORT VARCHAR2(10 BYTE) 
, PROCESS_STATUS VARCHAR2(20 BYTE) 
, LANCER_TOTAL_SCORE NUMBER 
, LANCER_QUALITY_SCORE NUMBER 
, LANCER_PROFESS_SCORE NUMBER 
, LANCER_COST_SCORE NUMBER 
, LANCER_PLAN_SCORE NUMBER 
, LANCER_RESPONS_SCORE NUMBER 
, LANCER_EVALUATION VARCHAR2(2100 BYTE) 
, CLIENT_TOTAL_SCORE VARCHAR2(20) 
, STATUS VARCHAR2(20 BYTE) 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT CONTRACT_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE CONTRACT_MILESTONE 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, PROJECT_ID VARCHAR2(32) 
, CONTRACT_ID VARCHAR2(32) 
, TITLE VARCHAR2(60 BYTE) 
, LOCATION VARCHAR2(21) 
, REMARK VARCHAR2(2100) 
, DATE_DELIVERY DATE 
, PRICE NUMBER 
, PROCESS VARCHAR2(20 BYTE) 
, STATUS VARCHAR2(10 BYTE) 
, PAY_STATUS VARCHAR2(20) 
, DATE_LATEST_PAY DATE 
, PAY_BILL_NUMBER VARCHAR2(50) 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT POST_JOB_MILESTONE_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE CONTRACT_REPORT 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, PROJECT_ID VARCHAR2(32 BYTE) 
, CONTRACT_ID VARCHAR2(32 BYTE) 
, DATE_RECORD DATE 
, WORK_CONTENT CLOB 
, WORK_HOURS NUMBER 
, WORK_REMARK VARCHAR2(2100 BYTE) 
, STATUS VARCHAR2(20 BYTE) 
, STATUS_REMARK VARCHAR2(2100) 
, PAY_BILL_NUMBER VARCHAR2(50) 
, CONSTRAINT PROJECT_DAILY_REPORT_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE FIN_PAY_BILL 
(
  PAY_BILL_NUMBER VARCHAR2(50 BYTE) NOT NULL 
, PAY_USER VARCHAR2(60 BYTE) 
, PAY_AMOUNT NUMBER 
, RECEIVE_USER VARCHAR2(60 BYTE) 
, RECEIVE_AMOUNT NUMBER 
, FEES NUMBER 
, CONSTRAINT PAY_NUMBER_PK PRIMARY KEY 
  (
    PAY_BILL_NUMBER 
  )
  ENABLE 
);

CREATE TABLE FIN_USER_ACCOUNT 
(
  USER_NAME VARCHAR2(60 BYTE) NOT NULL 
, AVALIABLE_AMOUNT NUMBER 
, FREEZE_AMOUNT NUMBER 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT FIN_USER_ACCOUNT_PK PRIMARY KEY 
  (
    USER_NAME 
  )
  ENABLE 
);

CREATE TABLE FIN_USER_ACCOUNT_LOG 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, MOVE_TARGET VARCHAR2(60) 
, MOVE_DIRECTION VARCHAR2(20) 
, MOVE_AMOUNT NUMBER 
, OTHER_FEE NUMBER 
, AFTER_ACCOUNT_AMOUNT NUMBER 
, BEFORE_ACCOUNT_AMOUNT NUMBER 
, DESCRIPTION VARCHAR2(2100 BYTE) 
, SUCCESS VARCHAR2(20) 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT FIN_ACCOUNT_LOG_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE JOB_CATEGORY 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, NAME_EN VARCHAR2(100 BYTE) 
, NAME_CN VARCHAR2(300 BYTE) 
, DISPLAY VARCHAR2(1 BYTE) 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT JOB_CATEGORY_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE JOB_SUB_CATEGORY 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, CATEGORY_ID VARCHAR2(32 BYTE) 
, NAME VARCHAR2(120 BYTE) 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT JOB_SUB_CATEGORY_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE JOB_TEMPLATE 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, JOB_CATEGORY_ID VARCHAR2(32 BYTE) 
, JOB_SUB_CATEGORY_ID VARCHAR2(32) 
, NAME_EN VARCHAR2(100 BYTE) 
, NAME_CN VARCHAR2(300 BYTE) 
, DESCRIPTION_CN CLOB 
, DESCRIPTION_EN CLOB 
, TIPS VARCHAR2(600 BYTE) 
, TYPE VARCHAR2(20 BYTE) 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT JOB_TEMPLATE_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE LOCATION_AREA 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, AREA_CODE VARCHAR2(10 BYTE) 
, AREA_NAME VARCHAR2(50 BYTE) 
, CITY_CODE VARCHAR2(10 BYTE) 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT LOCATION_AREA_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE LOCATION_CITY 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, CITY_CODE VARCHAR2(10 BYTE) 
, CITY_NAME VARCHAR2(60 BYTE) 
, PROVINCE_CODE VARCHAR2(10 BYTE) 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT LOCATION_CITY_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE LOCATION_COUNTRY 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, NAME VARCHAR2(60 BYTE) 
, ABB VARCHAR2(5 BYTE) 
, CODE VARCHAR2(10 BYTE) 
, NAME_LOC VARCHAR2(60 BYTE) 
, REGION_ID VARCHAR2(32) 
, SHOW_ORDER NUMBER 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT LOCATION_COUNTRY_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE LOCATION_PROVINCE 
(
  UUID VARCHAR2(32) NOT NULL 
, PROVINCE_CODE VARCHAR2(10 BYTE) 
, PROVINCE_NAME VARCHAR2(30 BYTE) 
, COUNTRY_ID VARCHAR2(32) 
, SHOW_ORDER NUMBER 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT LOCATION_PROVINCE_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE LOCATION_REGION 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, NAME_CN VARCHAR2(60 BYTE) 
, NAME_EN VARCHAR2(60 BYTE) 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT LOCATION_REGION_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE LOOKUPS 
(
  ITEM_KEY VARCHAR2(500 BYTE) NOT NULL 
, ITEM_DISPLAY VARCHAR2(500 BYTE) 
, ITEM_DESC VARCHAR2(900 BYTE) 
, ITEM_TYPE VARCHAR2(50 BYTE) NOT NULL 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT LOOKUPS_PK PRIMARY KEY 
  (
    ITEM_KEY 
  , ITEM_TYPE 
  )
  ENABLE 
);

CREATE TABLE POST_JOBS 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, NAME VARCHAR2(60 BYTE) 
, BRIEF VARCHAR2(2100 BYTE) 
, ATTACH VARCHAR2(200 BYTE) 
, POSTFORM VARCHAR2(20 BYTE) 
, HOURLY_PAY_MIN NUMBER 
, HOURLY_PAY_MAX NUMBER 
, DAY_PAY_MIN NUMBER 
, DAY_PAY_MAX NUMBER 
, WEEKLY_HOURS NUMBER 
, DURATION_MIN NUMBER 
, DURATION_MAX NUMBER 
, FIXED_PAY_MIN NUMBER 
, FIXED_PAY_MAX NUMBER 
, JOB_VISIBILITY NUMBER 
, ALLOW_SEARCH_ENGINES NUMBER 
, FIXED_LOCATION VARCHAR2(20 BYTE) 
, WORK_CATEGORY VARCHAR2(32 BYTE) 
, WORK_SUBCATEGORY VARCHAR2(32 BYTE) 
, SPECIFIC_SKILL_A VARCHAR2(32 BYTE) 
, SPECIFIC_SKILL_B VARCHAR2(32 BYTE) 
, SPECIFIC_SKILL_C VARCHAR2(32 BYTE) 
, SPECIFIC_SKILL_D VARCHAR2(32 BYTE) 
, SPECIFIC_SKILL_E VARCHAR2(32 BYTE) 
, SPECIFIC_SKILL_F VARCHAR2(32 BYTE) 
, SPECIFIC_SKILL_G VARCHAR2(32 BYTE) 
, STATUS VARCHAR2(20 BYTE) 
, POST_JOB_DATE_START DATE 
, POST_JOB_DATE_END DATE 
, LOCATION_COUNTRY VARCHAR2(32 BYTE) 
, LOCATION_PROVINCE VARCHAR2(32 BYTE) 
, LOCATION_CITY VARCHAR2(32 BYTE) 
, LOCATION_DESC VARCHAR2(1200 BYTE) 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT POST_JOBS_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE POST_JOBS_SKILL 
(
  POST_JOB_ID VARCHAR2(32 BYTE) NOT NULL 
, SKILL_ID VARCHAR2(32 BYTE) NOT NULL 
, REQUIRE_LEVEL NUMBER 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT POST_JOBS_SKILL_PK PRIMARY KEY 
  (
    POST_JOB_ID 
  , SKILL_ID 
  )
  ENABLE 
);

CREATE TABLE POST_JOB_DISCUSS 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, PARENT_DISCUSS_ID VARCHAR2(32 BYTE) 
, POST_JOB_ID VARCHAR2(32 BYTE) 
, CONTENT VARCHAR2(2100 BYTE) 
, IS_APPLY VARCHAR2(1 BYTE) 
, POSTFORM VARCHAR2(20 BYTE) 
, HOURLY_PAY NUMBER 
, WEEKLY_HOURS NUMBER 
, ENTERY_DATE DATE 
, TOTAL_PRICE NUMBER 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT POST_JOB_DISCUSS_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE PROJECT 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, POST_JOB_ID VARCHAR2(32 BYTE) 
, PROJECT_NAME VARCHAR2(60 BYTE) 
, DESCRIPTION CLOB 
, PROJECT_COMPANY_ID VARCHAR2(32 BYTE) 
, PARENT_PROJECT_ID VARCHAR2(32) 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT PROJECT_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE SKILLS 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, SUPER_TYPE_ID VARCHAR2(32 BYTE) 
, NAME VARCHAR2(120 BYTE) 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT SKILLS_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE SKILL_SUPER_TYPE 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, NAME VARCHAR2(60 BYTE) 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT SKILL_SUPER_TYPE_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE SPREAD_HEARD_FROM 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, TEXT VARCHAR2(2100 BYTE) 
, TYPE VARCHAR2(20 BYTE) 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT SPREAD_HEARD_FROM_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE SYS_CALENDAR 
(
  SYS_DATE DATE NOT NULL 
, DATE_TYPE VARCHAR2(20 BYTE) 
, DESCRIPTION VARCHAR2(20 BYTE) 
, DAY NUMBER 
, CONSTRAINT DAILY_CALENDAR_PK PRIMARY KEY 
  (
    SYS_DATE 
  )
  ENABLE 
);

CREATE TABLE USER_EDUCATION 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, USER_NAME VARCHAR2(32 BYTE) 
, INSTITUTION_NAME VARCHAR2(45 BYTE) 
, DEGREE_TYPE VARCHAR2(45 BYTE) 
, START_DATE DATE 
, END_DATE DATE 
, DESCRIPTION CLOB 
, ATTACH1_LINK VARCHAR2(500 BYTE) 
, ATTACH2_LINK VARCHAR2(500 BYTE) 
, ATTACH3_LINK VARCHAR2(500 BYTE) 
, TOBE_VERIFIED VARCHAR2(1 BYTE) 
, DONE_VERIFIED VARCHAR2(1 BYTE) 
, DISPLAY VARCHAR2(1 BYTE) 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT USER_EDUCATION_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE USER_LOCATION_LIST 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, USER_NAME VARCHAR2(60 BYTE) NOT NULL 
, REGION_ID VARCHAR2(32 BYTE) 
, COUNTRY_ID VARCHAR2(32 BYTE) 
, PROVINCE_ID VARCHAR2(32 BYTE) 
, CITY_ID VARCHAR2(32 BYTE) 
, AREA_ID VARCHAR2(32 BYTE) 
, CURRENT_IN NUMBER 
, TELPHONE VARCHAR2(20 BYTE) 
, FAX_NUMBER VARCHAR2(20 BYTE) 
, ZIP_CODE VARCHAR2(20 BYTE) 
, DETAIL_LOC VARCHAR2(100 BYTE) 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT USER_LOCATION_LIST_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE USER_ROLE 
(
  NAME VARCHAR2(120 BYTE) NOT NULL 
, DISPLAY_NAME VARCHAR2(120 BYTE) 
, TYPE VARCHAR2(20 BYTE) 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT USER_ROLE_PK PRIMARY KEY 
  (
    NAME 
  )
  ENABLE 
);

CREATE TABLE USER_ROLE_GRANTS 
(
  USER_NAME VARCHAR2(60 BYTE) NOT NULL 
, ROLE_NAME VARCHAR2(120 BYTE) NOT NULL 
, TYPE VARCHAR2(20 BYTE) 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT USER_ROLE_GRANTS_PK PRIMARY KEY 
  (
    USER_NAME 
  , ROLE_NAME 
  )
  ENABLE 
);

CREATE TABLE USER_SKILL 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, USER_NAME VARCHAR2(32 BYTE) 
, NAME VARCHAR2(150 BYTE) 
, DISPLAY NUMBER 
, SHOW_ORDER NUMBER 
, MASTER_LEVEL NUMBER 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT USER_SKILL_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE U_USER 
(
  USER_NAME VARCHAR2(60 BYTE) NOT NULL 
, TRUE_NAME VARCHAR2(60 BYTE) 
, DISPLAY_NAME VARCHAR2(60 BYTE) 
, EMAIL VARCHAR2(100 BYTE) 
, PASSWORD VARCHAR2(100 BYTE) 
, IMG VARCHAR2(600 BYTE) 
, COUNTRY VARCHAR2(32 BYTE) 
, COMPANY_ID VARCHAR2(32 BYTE) 
, PHONE_NUMBER VARCHAR2(20 BYTE) 
, ATTACH VARCHAR2(600 BYTE) 
, JOB_TITLE VARCHAR2(150 BYTE) 
, VIDEO VARCHAR2(600 BYTE) 
, DESCRIPTION CLOB 
, WEBSITE_URL VARCHAR2(600 BYTE) 
, IM_NUMBER_A VARCHAR2(60 BYTE) 
, IM_TYPE_A VARCHAR2(60 BYTE) 
, IM_NUMBER_B VARCHAR2(60 BYTE) 
, IM_TYPE_B VARCHAR2(60 BYTE) 
, IM_NUMBER_C VARCHAR2(60 BYTE) 
, IM_TYPE_C VARCHAR2(60 BYTE) 
, LOCATION_A VARCHAR2(32 BYTE) 
, LOCATION_B VARCHAR2(32 BYTE) 
, TAGLINE VARCHAR2(600 BYTE) 
, HOURLY_RATE NUMBER 
, CHARGE_RATE NUMBER 
, OVERVIEW VARCHAR2(3000 BYTE) 
, SERVICE_DESCRIPTION CLOB 
, PAYMENT_TERMS CLOB 
, KEYWORDS VARCHAR2(600 BYTE) 
, ADDRESS_DISPLAY VARCHAR2(20 BYTE) 
, CONTACT_INFO VARCHAR2(20 BYTE) 
, LAST_LOGIN_TIME DATE 
, CAN_BE_SEARCH NUMBER 
, DEFAULT_ROLE VARCHAR2(20 BYTE) 
, CREATE_BY VARCHAR2(60 BYTE) 
, CREATE_ON DATE 
, MODIFY_BY VARCHAR2(60 BYTE) 
, MODIFY_ON DATE 
, VERSION NUMBER 
, CONSTRAINT U_USER_PK PRIMARY KEY 
  (
    USER_NAME 
  )
  ENABLE 
);

CREATE INDEX LOCATION_COUNTRY_INDEX1 ON LOCATION_COUNTRY (SHOW_ORDER ASC);

CREATE INDEX LOCATION_COUNTRY_INDEX2 ON LOCATION_COUNTRY (NAME ASC);

CREATE UNIQUE INDEX U_USER_EMAIL_IDX ON U_USER (EMAIL ASC);

ALTER TABLE U_USER
ADD CONSTRAINT U_USER_EMAIL_UK UNIQUE 
(
  EMAIL 
)
USING INDEX U_USER_EMAIL_IDX
ENABLE;

ALTER TABLE CONTRACT
ADD CONSTRAINT CONTRACT_PROJECT_FK1 FOREIGN KEY
(
  PROJECT_ID 
)
REFERENCES PROJECT
(
  UUID 
)
ENABLE;

ALTER TABLE CONTRACT
ADD CONSTRAINT CONTRACT_U_USER_FK1 FOREIGN KEY
(
  CLIENT_NAME 
)
REFERENCES U_USER
(
  USER_NAME 
)
ENABLE;

ALTER TABLE CONTRACT
ADD CONSTRAINT CONTRACT_U_USER_FK2 FOREIGN KEY
(
  LANCER_NAME 
)
REFERENCES U_USER
(
  USER_NAME 
)
ENABLE;

ALTER TABLE CONTRACT_MILESTONE
ADD CONSTRAINT CONTRACT_MILESTONE_FK1 FOREIGN KEY
(
  CONTRACT_ID 
)
REFERENCES CONTRACT
(
  UUID 
)
ENABLE;

ALTER TABLE CONTRACT_MILESTONE
ADD CONSTRAINT CONTRACT_MILESTONE_FK2 FOREIGN KEY
(
  PAY_BILL_NUMBER 
)
REFERENCES FIN_PAY_BILL
(
  PAY_BILL_NUMBER 
)
ENABLE;

ALTER TABLE CONTRACT_MILESTONE
ADD CONSTRAINT PROJECT_MILESTONE_FK1 FOREIGN KEY
(
  DATE_LATEST_PAY 
)
REFERENCES SYS_CALENDAR
(
  SYS_DATE 
)
ENABLE;

ALTER TABLE CONTRACT_REPORT
ADD CONSTRAINT CONTRACT_REPORT_CONTRACT_FK1 FOREIGN KEY
(
  CONTRACT_ID 
)
REFERENCES CONTRACT
(
  UUID 
)
ENABLE;

ALTER TABLE CONTRACT_REPORT
ADD CONSTRAINT CONTRACT_REPORT_FK1 FOREIGN KEY
(
  PAY_BILL_NUMBER 
)
REFERENCES FIN_PAY_BILL
(
  PAY_BILL_NUMBER 
)
ENABLE;

ALTER TABLE CONTRACT_REPORT
ADD CONSTRAINT PROJECT_DAILY_REPORT_FK1 FOREIGN KEY
(
  DATE_RECORD 
)
REFERENCES SYS_CALENDAR
(
  SYS_DATE 
)
ENABLE;

ALTER TABLE FIN_PAY_BILL
ADD CONSTRAINT FIN_PAY_BILL_FK1 FOREIGN KEY
(
  PAY_USER 
)
REFERENCES FIN_USER_ACCOUNT
(
  USER_NAME 
)
ENABLE;

ALTER TABLE FIN_PAY_BILL
ADD CONSTRAINT FIN_PAY_BILL_FK2 FOREIGN KEY
(
  RECEIVE_USER 
)
REFERENCES FIN_USER_ACCOUNT
(
  USER_NAME 
)
ENABLE;

ALTER TABLE JOB_SUB_CATEGORY
ADD CONSTRAINT JOB_SUB_CATEGORY_FK1 FOREIGN KEY
(
  CATEGORY_ID 
)
REFERENCES JOB_CATEGORY
(
  UUID 
)
ENABLE;

ALTER TABLE LOCATION_COUNTRY
ADD CONSTRAINT LOCATION_COUNTRY_FK1 FOREIGN KEY
(
  REGION_ID 
)
REFERENCES LOCATION_REGION
(
  UUID 
)
ENABLE;

ALTER TABLE LOCATION_PROVINCE
ADD CONSTRAINT LOCATION_PROVINCE_FK1 FOREIGN KEY
(
  COUNTRY_ID 
)
REFERENCES LOCATION_COUNTRY
(
  UUID 
)
ENABLE;

ALTER TABLE POST_JOBS_SKILL
ADD CONSTRAINT POST_JOBS_SKILL_POST_JOBS_FK1 FOREIGN KEY
(
  POST_JOB_ID 
)
REFERENCES POST_JOBS
(
  UUID 
)
ENABLE;

ALTER TABLE POST_JOBS_SKILL
ADD CONSTRAINT POST_JOBS_SKILL_SKILLS_FK1 FOREIGN KEY
(
  SKILL_ID 
)
REFERENCES SKILLS
(
  UUID 
)
ENABLE;

ALTER TABLE POST_JOB_DISCUSS
ADD CONSTRAINT POST_JOB_DISCUSS_FK1 FOREIGN KEY
(
  POST_JOB_ID 
)
REFERENCES POST_JOBS
(
  UUID 
)
ENABLE;

ALTER TABLE PROJECT
ADD CONSTRAINT PROJECT_PROJECT_FK1 FOREIGN KEY
(
  PARENT_PROJECT_ID 
)
REFERENCES PROJECT
(
  UUID 
)
ENABLE;

ALTER TABLE SKILLS
ADD CONSTRAINT SKILLS_SKILL_SUPER_TYPE_FK1 FOREIGN KEY
(
  SUPER_TYPE_ID 
)
REFERENCES SKILL_SUPER_TYPE
(
  UUID 
)
ENABLE;

ALTER TABLE USER_EDUCATION
ADD CONSTRAINT USER_EDUCATION_U_USER_FK1 FOREIGN KEY
(
  USER_NAME 
)
REFERENCES U_USER
(
  USER_NAME 
)
ENABLE;

ALTER TABLE USER_LOCATION_LIST
ADD CONSTRAINT USER_LOCATION_LIST_U_USER_FK1 FOREIGN KEY
(
  USER_NAME 
)
REFERENCES U_USER
(
  USER_NAME 
)
ENABLE;

ALTER TABLE USER_ROLE_GRANTS
ADD CONSTRAINT USER_ROLE_GRANTS_FK1 FOREIGN KEY
(
  ROLE_NAME 
)
REFERENCES USER_ROLE
(
  NAME 
)
ENABLE;

ALTER TABLE USER_ROLE_GRANTS
ADD CONSTRAINT USER_ROLE_GRANTS_U_USER_FK1 FOREIGN KEY
(
  USER_NAME 
)
REFERENCES U_USER
(
  USER_NAME 
)
ENABLE;

ALTER TABLE USER_SKILL
ADD CONSTRAINT USER_SKILL_U_USER_FK1 FOREIGN KEY
(
  USER_NAME 
)
REFERENCES U_USER
(
  USER_NAME 
)
ENABLE;

COMMENT ON TABLE COMPANY IS '公司';

COMMENT ON TABLE CONTRACT IS '项目人员评价';

COMMENT ON TABLE CONTRACT_MILESTONE IS '项目里程碑';

COMMENT ON TABLE FIN_USER_ACCOUNT IS '用户账户总表';

COMMENT ON TABLE FIN_USER_ACCOUNT_LOG IS '账户变动记录';

COMMENT ON TABLE JOB_CATEGORY IS '工作大类';

COMMENT ON TABLE JOB_SUB_CATEGORY IS '工作小类';

COMMENT ON TABLE JOB_TEMPLATE IS '招聘信息发布参考模版';

COMMENT ON TABLE LOCATION_AREA IS '地址-城市下的区域';

COMMENT ON TABLE LOCATION_CITY IS '所有城市';

COMMENT ON TABLE LOCATION_COUNTRY IS '所有国家';

COMMENT ON TABLE LOCATION_PROVINCE IS '所有省';

COMMENT ON TABLE LOCATION_REGION IS '地区：大洲';

COMMENT ON TABLE LOOKUPS IS '值集';

COMMENT ON TABLE POST_JOBS IS '发布招聘信息';

COMMENT ON TABLE POST_JOBS_SKILL IS '招聘信息对应技能需求';

COMMENT ON TABLE POST_JOB_DISCUSS IS '已发布招聘信息讨论';

COMMENT ON TABLE PROJECT IS '合约';

COMMENT ON TABLE SKILLS IS '全部技能';

COMMENT ON TABLE SKILL_SUPER_TYPE IS '技能大类';

COMMENT ON TABLE SPREAD_HEARD_FROM IS '本网站口碑、传播信息收集';

COMMENT ON TABLE USER_EDUCATION IS '教育信息';

COMMENT ON TABLE USER_LOCATION_LIST IS '用户保存的地址列表';

COMMENT ON TABLE USER_ROLE IS '角色';

COMMENT ON TABLE USER_ROLE_GRANTS IS '用户角色授权表';

COMMENT ON TABLE USER_SKILL IS '用户技能';

COMMENT ON TABLE U_USER IS '人员主表';

COMMENT ON COLUMN COMPANY.NAME IS '公司名';

COMMENT ON COLUMN COMPANY.ENTERPRISE_PROPERTY IS '企业性质';

COMMENT ON COLUMN COMPANY.EMPLOYEE_NUMBER_GRADE IS '员工数级别';

COMMENT ON COLUMN COMPANY.FOUND_YEAR IS '成立时间';

COMMENT ON COLUMN COMPANY.ABOUT_COMPANY IS '公司简介';

COMMENT ON COLUMN COMPANY.SERVICE_DESC IS '服务内容';

COMMENT ON COLUMN COMPANY.VIDEO IS '视频';

COMMENT ON COLUMN COMPANY.LOGO IS 'Logo图片位置';

COMMENT ON COLUMN COMPANY.LOCATION_COUNTRY IS '公司位置';

COMMENT ON COLUMN COMPANY.LOCATION_PROVINCE IS '公司位置';

COMMENT ON COLUMN COMPANY.LOCATION_CITY IS '公司位置';

COMMENT ON COLUMN COMPANY.LOCATION_DESC IS '公司位置';

COMMENT ON COLUMN CONTRACT.TITLE IS '合约名称';

COMMENT ON COLUMN CONTRACT.CONTENT IS '内容';

COMMENT ON COLUMN CONTRACT.ATTACHMENT_LINK IS '附件链接';

COMMENT ON COLUMN CONTRACT.CLIENT_NAME IS '甲方';

COMMENT ON COLUMN CONTRACT.LANCER_NAME IS '乙方';

COMMENT ON COLUMN CONTRACT.DATE_START IS '生效开始时间';

COMMENT ON COLUMN CONTRACT.DATE_END IS '生效结束时间';

COMMENT ON COLUMN CONTRACT.CLIENT_ROLE_TITLE IS '甲方角色名';

COMMENT ON COLUMN CONTRACT.LANCER_ROLE_TITLE IS '乙方角色名';

COMMENT ON COLUMN CONTRACT.POSTFORM IS '支付方式';

COMMENT ON COLUMN CONTRACT.HOURLY_PAY IS '每小时价格（按时支付）';

COMMENT ON COLUMN CONTRACT.WEEKLY_HOURS IS '每周工作小时数（按时支付）';

COMMENT ON COLUMN CONTRACT.FIXED_PAY_PRICE IS '固定支付金额（固定价格）';

COMMENT ON COLUMN CONTRACT.CLIENT_CONFIRM_DELAY_DAY IS '乙方完成~甲方确认间隔';

COMMENT ON COLUMN CONTRACT.CLIENT_PAY_DELAY_DAY IS '甲方确认~支付间隔';

COMMENT ON COLUMN CONTRACT.FIXED_PAY_DATE IS '每月固定支付日';

COMMENT ON COLUMN CONTRACT.FIXED_PAY_DELAY_HOLIDAY IS '每月固定支付日—是否遇假日顺延';

COMMENT ON COLUMN CONTRACT.NEED_DAILY_REPORT IS '是否需要日志';

COMMENT ON COLUMN CONTRACT.PROCESS_STATUS IS '草稿，进行中，完成等';

COMMENT ON COLUMN CONTRACT.LANCER_TOTAL_SCORE IS '甲方对乙方总评';

COMMENT ON COLUMN CONTRACT.LANCER_QUALITY_SCORE IS '乙方工作质量';

COMMENT ON COLUMN CONTRACT.LANCER_PROFESS_SCORE IS '乙方工作专业';

COMMENT ON COLUMN CONTRACT.LANCER_COST_SCORE IS '乙方工作成本';

COMMENT ON COLUMN CONTRACT.LANCER_PLAN_SCORE IS '乙方工作计划';

COMMENT ON COLUMN CONTRACT.LANCER_RESPONS_SCORE IS '乙方工作责任';

COMMENT ON COLUMN CONTRACT.LANCER_EVALUATION IS '乙方工作附加描述';

COMMENT ON COLUMN CONTRACT.CLIENT_TOTAL_SCORE IS '乙方对甲方总评';

COMMENT ON COLUMN CONTRACT_MILESTONE.TITLE IS '名称';

COMMENT ON COLUMN CONTRACT_MILESTONE.LOCATION IS '地址';

COMMENT ON COLUMN CONTRACT_MILESTONE.REMARK IS '备忘、备注';

COMMENT ON COLUMN CONTRACT_MILESTONE.DATE_DELIVERY IS '交付日期';

COMMENT ON COLUMN CONTRACT_MILESTONE.PRICE IS '金额';

COMMENT ON COLUMN CONTRACT_MILESTONE.PROCESS IS '进度：draft,done，doing，todo';

COMMENT ON COLUMN CONTRACT_MILESTONE.STATUS IS '--未确定用途';

COMMENT ON COLUMN CONTRACT_MILESTONE.PAY_STATUS IS '未支付、已支付、已到帐';

COMMENT ON COLUMN CONTRACT_MILESTONE.DATE_LATEST_PAY IS '最迟支付日期';

COMMENT ON COLUMN CONTRACT_MILESTONE.PAY_BILL_NUMBER IS '支付单号';

COMMENT ON COLUMN CONTRACT_REPORT.DATE_RECORD IS '年月日';

COMMENT ON COLUMN CONTRACT_REPORT.WORK_CONTENT IS '日报内容';

COMMENT ON COLUMN CONTRACT_REPORT.WORK_HOURS IS '工时';

COMMENT ON COLUMN CONTRACT_REPORT.WORK_REMARK IS '备注';

COMMENT ON COLUMN CONTRACT_REPORT.STATUS IS '确认、拒绝';

COMMENT ON COLUMN CONTRACT_REPORT.STATUS_REMARK IS '确认情况备注';

COMMENT ON COLUMN FIN_USER_ACCOUNT.AVALIABLE_AMOUNT IS '可用余额';

COMMENT ON COLUMN FIN_USER_ACCOUNT.FREEZE_AMOUNT IS '冻结余额';

COMMENT ON COLUMN FIN_USER_ACCOUNT_LOG.MOVE_TARGET IS '银行名等';

COMMENT ON COLUMN FIN_USER_ACCOUNT_LOG.MOVE_DIRECTION IS 'in;out';

COMMENT ON COLUMN FIN_USER_ACCOUNT_LOG.MOVE_AMOUNT IS '金额';

COMMENT ON COLUMN FIN_USER_ACCOUNT_LOG.OTHER_FEE IS '第三方手续费';

COMMENT ON COLUMN FIN_USER_ACCOUNT_LOG.AFTER_ACCOUNT_AMOUNT IS '操作后剩余金额';

COMMENT ON COLUMN FIN_USER_ACCOUNT_LOG.BEFORE_ACCOUNT_AMOUNT IS '操前剩余金额';

COMMENT ON COLUMN FIN_USER_ACCOUNT_LOG.DESCRIPTION IS '描述';

COMMENT ON COLUMN FIN_USER_ACCOUNT_LOG.SUCCESS IS '操作是否Y成功，N失败';

COMMENT ON COLUMN JOB_TEMPLATE.TYPE IS '1职位 2项目';

COMMENT ON COLUMN POST_JOBS.POSTFORM IS '1时薪/2固定价格';

COMMENT ON COLUMN POST_JOBS.HOURLY_PAY_MIN IS '时薪：薪资范围';

COMMENT ON COLUMN POST_JOBS.WEEKLY_HOURS IS '时薪：hrs/week input';

COMMENT ON COLUMN POST_JOBS.DURATION_MIN IS '时薪：项目周期(周)';

COMMENT ON COLUMN POST_JOBS.FIXED_PAY_MIN IS '固定价格：价格';

COMMENT ON COLUMN POST_JOBS.JOB_VISIBILITY IS 'public/private';

COMMENT ON COLUMN POST_JOBS.ALLOW_SEARCH_ENGINES IS '利用搜索引擎推广';

COMMENT ON COLUMN POST_JOBS.FIXED_LOCATION IS '是否指定地点';

COMMENT ON COLUMN POST_JOBS.WORK_CATEGORY IS '工作大类';

COMMENT ON COLUMN POST_JOBS.WORK_SUBCATEGORY IS '工作细分小类';

COMMENT ON COLUMN POST_JOBS.SPECIFIC_SKILL_A IS '特殊技能需求';

COMMENT ON COLUMN POST_JOBS.STATUS IS '1:草稿，2：发布，0：删除';

COMMENT ON COLUMN POST_JOBS.POST_JOB_DATE_START IS '需求发布开始时间';

COMMENT ON COLUMN POST_JOBS.POST_JOB_DATE_END IS '需求发布结束时间';

COMMENT ON COLUMN POST_JOBS.LOCATION_DESC IS '地点额外说明';

COMMENT ON COLUMN POST_JOBS_SKILL.REQUIRE_LEVEL IS '此技能所需级别';

COMMENT ON COLUMN POST_JOB_DISCUSS.IS_APPLY IS '是否申请此工作Y/N';

COMMENT ON COLUMN POST_JOB_DISCUSS.POSTFORM IS '1时薪/2固定价格';

COMMENT ON COLUMN POST_JOB_DISCUSS.HOURLY_PAY IS '报价：时薪';

COMMENT ON COLUMN POST_JOB_DISCUSS.WEEKLY_HOURS IS '每周工作时间';

COMMENT ON COLUMN POST_JOB_DISCUSS.ENTERY_DATE IS '可进入时间';

COMMENT ON COLUMN POST_JOB_DISCUSS.TOTAL_PRICE IS '总价（固定价格）';

COMMENT ON COLUMN PROJECT.PROJECT_COMPANY_ID IS '项目所属公司';

COMMENT ON COLUMN SYS_CALENDAR.SYS_DATE IS '年月日';

COMMENT ON COLUMN SYS_CALENDAR.DATE_TYPE IS '工作日，周末，法定假日，临时假日等';

COMMENT ON COLUMN SYS_CALENDAR.DAY IS '星期几';

COMMENT ON COLUMN USER_EDUCATION.INSTITUTION_NAME IS '机构名';

COMMENT ON COLUMN USER_EDUCATION.DEGREE_TYPE IS '学位信息';

COMMENT ON COLUMN USER_EDUCATION.DESCRIPTION IS '学习内容描述';

COMMENT ON COLUMN USER_EDUCATION.ATTACH1_LINK IS '附件';

COMMENT ON COLUMN USER_EDUCATION.TOBE_VERIFIED IS '是否需要验证（Y/N）';

COMMENT ON COLUMN USER_EDUCATION.DONE_VERIFIED IS '是否完成验证（Y/N）';

COMMENT ON COLUMN USER_EDUCATION.DISPLAY IS '是否公开显示（Y/N）';

COMMENT ON COLUMN USER_SKILL.NAME IS '技能名称';

COMMENT ON COLUMN USER_SKILL.DISPLAY IS '是否显示';

COMMENT ON COLUMN USER_SKILL.SHOW_ORDER IS '显示顺序';

COMMENT ON COLUMN USER_SKILL.MASTER_LEVEL IS '掌握程度';

COMMENT ON COLUMN U_USER.DESCRIPTION IS '个人描述';

COMMENT ON COLUMN U_USER.LOCATION_A IS '用户主要地址';

COMMENT ON COLUMN U_USER.LOCATION_B IS '用户次要地址';

COMMENT ON COLUMN U_USER.HOURLY_RATE IS 'Lancer提出的每小时价格';

COMMENT ON COLUMN U_USER.CHARGE_RATE IS '网站会向客户提出的价格';

COMMENT ON COLUMN U_USER.OVERVIEW IS '简历上的简短描述';

COMMENT ON COLUMN U_USER.SERVICE_DESCRIPTION IS '服务描述';

COMMENT ON COLUMN U_USER.PAYMENT_TERMS IS '支付方式描述';

COMMENT ON COLUMN U_USER.KEYWORDS IS '英文逗号分隔的关键词';

COMMENT ON COLUMN U_USER.ADDRESS_DISPLAY IS '地址显示级别：all，city，no';

COMMENT ON COLUMN U_USER.LAST_LOGIN_TIME IS '最后登录时间';

COMMENT ON COLUMN U_USER.CAN_BE_SEARCH IS '可被搜索';

COMMENT ON COLUMN U_USER.DEFAULT_ROLE IS '当前用户默认角色，用于默认页面展示';
