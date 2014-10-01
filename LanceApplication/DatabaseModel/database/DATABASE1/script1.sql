SET SQLBLANKLINES ON
CREATE TABLE CLIENT_COMPANY 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, NAME VARCHAR2(100 BYTE) 
, ENTERPRISE_PROPERTY VARCHAR2(32 BYTE) 
, EMPLOYEE_NUMBER_GRADE NUMBER 
, FOUND_YEAR DATE 
, ABOUT_COMPANY VARCHAR2(20 BYTE) 
, SERVICE_DESC VARCHAR2(20 BYTE) 
, LOCATION VARCHAR2(32 BYTE) 
, VIDEO VARCHAR2(200 BYTE) 
, LOGO VARCHAR2(200 BYTE) 
, CONSTRAINT CLIENT_COMPANY_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE CLIENT_USER 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, EMAIL VARCHAR2(50 BYTE) 
, TRUST_CHECK VARCHAR2(20 BYTE) 
, TELPHONE VARCHAR2(50 BYTE) 
, MOBILE_PHONE VARCHAR2(50 BYTE) 
, BRIEF VARCHAR2(2000 BYTE) 
, ATTACH VARCHAR2(200 BYTE) 
, JOB_TITLE VARCHAR2(20 BYTE) 
, COMPANY_ID VARCHAR2(32 BYTE) 
, VIDEO VARCHAR2(200 BYTE) 
, IMAGE VARCHAR2(200 BYTE) 
, USER_NAME VARCHAR2(50 BYTE) 
, PASSWORD VARCHAR2(50 BYTE) 
, DISPLAY_NAME VARCHAR2(50 BYTE) 
, TRUE_NAME VARCHAR2(50 BYTE) 
, CONSTRAINT CLIENT_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE LANCER 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, IMG VARCHAR2(500 BYTE) 
, USER_NAME VARCHAR2(100 BYTE) 
, EMAIL VARCHAR2(100 BYTE) 
, PASSWORD VARCHAR2(100 BYTE) 
, DISPLAY_NAME VARCHAR2(20 BYTE) 
, COUNTRY VARCHAR2(32 BYTE) 
, TRUE_NAME VARCHAR2(50 BYTE) 
, ACCOUNT_TYPE NUMBER 
, COMPANY_NAME VARCHAR2(50 BYTE) 
, CONSTRAINT LANCER_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE LANCER_LOCATION_LIST 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, REGION_ID VARCHAR2(20 BYTE) 
, COUNTRY_ID VARCHAR2(32 BYTE) 
, PROVINCE_ID VARCHAR2(32 BYTE) 
, CITY_ID VARCHAR2(32 BYTE) 
, AREA_ID VARCHAR2(32 BYTE) 
, CURRENT_IN NUMBER 
, LANCER_ID VARCHAR2(32 BYTE) 
, CONSTRAINT LOCATION_CUSTOM_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE LANCER_RESUME 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, SKILL_A_TAG VARCHAR2(1000 BYTE) 
, SKILL_B_TAG VARCHAR2(1000 BYTE) 
, SKILL_C_TAG VARCHAR2(1000 BYTE) 
, SKILL_TYPE_ID VARCHAR2(32 BYTE) 
, BRIEF VARCHAR2(2000 BYTE) 
, WORK_EXP CLOB 
, EDU_EXP CLOB 
, LANCER_ID VARCHAR2(32 BYTE) 
, VIDEO VARCHAR2(200 BYTE) 
, CONSTRAINT LANCER_RESUME_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE LANCE_EXP 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, LANCER_ID VARCHAR2(32 BYTE) 
, ATTACH VARCHAR2(200 BYTE) 
, CLIENT_ID VARCHAR2(32 BYTE) 
, BRIEF VARCHAR2(2000 BYTE) 
, GRADE_EXPERTISE NUMBER 
, GRADE_PROFESSIONAL NUMBER 
, GRADE_RESPONSE NUMBER 
, GRADE_SCHEDULE NUMBER 
, GRADE_QUALITY NUMBER 
, GRADE_COST NUMBER 
, GRADE_TOTAL NUMBER 
, VIDEO VARCHAR2(200 BYTE) 
, CONSTRAINT LANCE_EXP_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE LANCE_EXP_COMMENT 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, LANCE_EXP_ID VARCHAR2(32 BYTE) 
, TEXT VARCHAR2(2000 BYTE) 
, CONSTRAINT LANCE_EXP_EVALUATE_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE LOCATION_AREA 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, AREA_CODE VARCHAR2(6 BYTE) 
, AREA_NAME VARCHAR2(50 BYTE) 
, CITY_CODE VARCHAR2(6 BYTE) 
, CONSTRAINT LOCATION_AREA_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE LOCATION_CITY 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, CITY_CODE VARCHAR2(6 BYTE) 
, CITY_NAME VARCHAR2(50 BYTE) 
, PROVINCE_CODE VARCHAR2(6 BYTE) 
, CONSTRAINT LOCATION_CITY_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE LOCATION_COUNTRY 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, NAME VARCHAR2(50 BYTE) 
, ABB VARCHAR2(5 BYTE) 
, CODE VARCHAR2(5 BYTE) 
, NAME_LOC VARCHAR2(50 BYTE) 
, SHOW_ORDER NUMBER 
, CONSTRAINT LOCATION_COUNTRY_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE LOCATION_PROVINCE 
(
  UUID NUMBER NOT NULL 
, PROVINCE_CODE VARCHAR2(10 BYTE) 
, PROVINCE_NAME VARCHAR2(30 BYTE) 
, CONSTRAINT LOCATION_PROVINCE_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE LOCATION_REGION 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, NAME_CN VARCHAR2(20 BYTE) 
, NAME_EN VARCHAR2(50 BYTE) 
, CONSTRAINT LOCATION_REGION_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE POST_JOBS 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, NAME VARCHAR2(50 BYTE) 
, BRIEF VARCHAR2(2000 BYTE) 
, ATTACH VARCHAR2(200 BYTE) 
, WORK_SUBCATEGORY VARCHAR2(32 BYTE) 
, SKILLS VARCHAR2(1000 BYTE) 
, POSTFORM NUMBER 
, HOURLY_RATE_GRADE NUMBER 
, WEEKLY_HOURS NUMBER 
, DURATION NUMBER 
, FIXED_BUDGET NUMBER 
, VISIBILITY NUMBER 
, SEO_INDEX_FIELD NUMBER 
, FIXED_LOCATION NUMBER 
, LOCATION_ID VARCHAR2(32 BYTE) 
, LOCATION_DESC VARCHAR2(500 BYTE) 
, CONSTRAINT POST_JOBS_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE SKILLS 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, SUPER_TYPE_ID VARCHAR2(32 BYTE) 
, NAME VARCHAR2(50 BYTE) 
, CONSTRAINT SKILLS_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE TABLE SKILL_SUPER_TYPE 
(
  UUID VARCHAR2(32 BYTE) NOT NULL 
, NAME VARCHAR2(50 BYTE) 
, CONSTRAINT SKILL_SUPER_TYPE_PK PRIMARY KEY 
  (
    UUID 
  )
  ENABLE 
);

CREATE INDEX CLIENT_USER_UNIQUE1 ON CLIENT_USER (EMAIL ASC);

CREATE INDEX CLIENT_USER_UNIQUE2 ON CLIENT_USER (USER_NAME ASC);

CREATE UNIQUE INDEX LANCER_UNIQUE1 ON LANCER (USER_NAME ASC);

CREATE UNIQUE INDEX LANCER_UNIQUE2 ON LANCER (EMAIL ASC);

CREATE INDEX LOCATION_COUNTRY_INDEX1 ON LOCATION_COUNTRY (SHOW_ORDER ASC);

CREATE INDEX LOCATION_COUNTRY_INDEX2 ON LOCATION_COUNTRY (NAME ASC);

ALTER TABLE CLIENT_USER
ADD CONSTRAINT CLIENT_USER_FK1 FOREIGN KEY
(
  COMPANY_ID 
)
REFERENCES CLIENT_COMPANY
(
  UUID 
)
ENABLE;

ALTER TABLE LANCER_RESUME
ADD CONSTRAINT LANCER_RESUME_LANCER_FK1 FOREIGN KEY
(
  LANCER_ID 
)
REFERENCES LANCER
(
  UUID 
)
ENABLE;

ALTER TABLE LANCE_EXP
ADD CONSTRAINT LANCE_EXP_CLIENT_FK1 FOREIGN KEY
(
  CLIENT_ID 
)
REFERENCES CLIENT_USER
(
  UUID 
)
ENABLE;

ALTER TABLE LANCE_EXP
ADD CONSTRAINT LANCE_EXP_FK1 FOREIGN KEY
(
  LANCER_ID 
)
REFERENCES LANCER_LOCATION_LIST
(
  UUID 
)
ENABLE;

ALTER TABLE LANCE_EXP
ADD CONSTRAINT LANCE_EXP_LANCER_RESUME_FK1 FOREIGN KEY
(
  LANCER_ID 
)
REFERENCES LANCER_RESUME
(
  UUID 
)
ENABLE;

ALTER TABLE LANCE_EXP_COMMENT
ADD CONSTRAINT LANCE_EXP_COMMENT_FK1 FOREIGN KEY
(
  LANCE_EXP_ID 
)
REFERENCES LANCE_EXP
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

COMMENT ON TABLE LANCE_EXP_COMMENT IS '针对本次工作的评论，含client与lancer的对话';

COMMENT ON COLUMN CLIENT_COMPANY.ENTERPRISE_PROPERTY IS '企业性质';

COMMENT ON COLUMN CLIENT_COMPANY.EMPLOYEE_NUMBER_GRADE IS '员工数级别';

COMMENT ON COLUMN CLIENT_USER.TRUST_CHECK IS '认证信息';

COMMENT ON COLUMN CLIENT_USER.BRIEF IS '公司简介';

COMMENT ON COLUMN CLIENT_USER.JOB_TITLE IS '头衔';

COMMENT ON COLUMN LANCER.ACCOUNT_TYPE IS 'Individual/Company';

COMMENT ON COLUMN LANCER_RESUME.SKILL_TYPE_ID IS '技能类型';

COMMENT ON COLUMN LANCER_RESUME.BRIEF IS '个人简介';

COMMENT ON COLUMN LANCER_RESUME.WORK_EXP IS '工作经历';

COMMENT ON COLUMN LANCER_RESUME.EDU_EXP IS '教育经历';

COMMENT ON COLUMN POST_JOBS.POSTFORM IS '时薪/固定价格';

COMMENT ON COLUMN POST_JOBS.HOURLY_RATE_GRADE IS '时薪：薪资范围';

COMMENT ON COLUMN POST_JOBS.WEEKLY_HOURS IS '时薪：hrs/week input
';

COMMENT ON COLUMN POST_JOBS.DURATION IS '时薪：项目周期';

COMMENT ON COLUMN POST_JOBS.FIXED_BUDGET IS '固定价格：价格';

COMMENT ON COLUMN POST_JOBS.VISIBILITY IS 'public/private';

COMMENT ON COLUMN POST_JOBS.SEO_INDEX_FIELD IS '利用搜索引擎推广';

COMMENT ON COLUMN POST_JOBS.FIXED_LOCATION IS '是否指定地点';

COMMENT ON COLUMN POST_JOBS.LOCATION_DESC IS '额外要求';
