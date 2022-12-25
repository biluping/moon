/*
 Navicat Premium Data Transfer

 Source Server         : pg-mac
 Source Server Type    : PostgreSQL
 Source Server Version : 150001 (150001)
 Source Host           : localhost:5432
 Source Catalog        : moon
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 150001 (150001)
 File Encoding         : 65001

 Date: 25/12/2022 16:51:30
*/


-- ----------------------------
-- Sequence structure for moon_app_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."moon_app_id_seq";
CREATE SEQUENCE "public"."moon_app_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "public"."moon_app_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for moon_config_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."moon_config_id_seq";
CREATE SEQUENCE "public"."moon_config_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "public"."moon_config_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for moon_name_space_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."moon_name_space_id_seq";
CREATE SEQUENCE "public"."moon_name_space_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "public"."moon_name_space_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Table structure for moon_app
-- ----------------------------
DROP TABLE IF EXISTS "public"."moon_app";
CREATE TABLE "public"."moon_app" (
  "id" int8 NOT NULL DEFAULT nextval('moon_app_id_seq'::regclass),
  "appid" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "describe" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL DEFAULT now(),
  "update_time" timestamp(6) NOT NULL DEFAULT now()
)
;
ALTER TABLE "public"."moon_app" OWNER TO "postgres";
COMMENT ON COLUMN "public"."moon_app"."id" IS 'id';
COMMENT ON COLUMN "public"."moon_app"."appid" IS '应用id';
COMMENT ON COLUMN "public"."moon_app"."describe" IS '描述';
COMMENT ON COLUMN "public"."moon_app"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."moon_app"."update_time" IS '修改时间';

-- ----------------------------
-- Table structure for moon_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."moon_config";
CREATE TABLE "public"."moon_config" (
  "id" int8 NOT NULL DEFAULT nextval('moon_config_id_seq'::regclass),
  "appid" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "key" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "value" varchar(500) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL DEFAULT now(),
  "update_time" timestamp(6) NOT NULL DEFAULT now(),
  "is_publish" bool NOT NULL DEFAULT false,
  "name_space_id" int8 NOT NULL
)
;
ALTER TABLE "public"."moon_config" OWNER TO "postgres";
COMMENT ON COLUMN "public"."moon_config"."id" IS 'id';
COMMENT ON COLUMN "public"."moon_config"."appid" IS '应用唯一标识名称';
COMMENT ON COLUMN "public"."moon_config"."key" IS '配置键';
COMMENT ON COLUMN "public"."moon_config"."value" IS '配置值';
COMMENT ON COLUMN "public"."moon_config"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."moon_config"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."moon_config"."is_publish" IS '是否发布';
COMMENT ON COLUMN "public"."moon_config"."name_space_id" IS '名称空间id';

-- ----------------------------
-- Table structure for moon_name_space
-- ----------------------------
DROP TABLE IF EXISTS "public"."moon_name_space";
CREATE TABLE "public"."moon_name_space" (
  "id" int8 NOT NULL DEFAULT nextval('moon_name_space_id_seq'::regclass),
  "appid" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL DEFAULT now(),
  "update_time" timestamp(6) NOT NULL DEFAULT now()
)
;
ALTER TABLE "public"."moon_name_space" OWNER TO "postgres";
COMMENT ON COLUMN "public"."moon_name_space"."id" IS 'id';
COMMENT ON COLUMN "public"."moon_name_space"."appid" IS '应用id';
COMMENT ON COLUMN "public"."moon_name_space"."name" IS '名称空间';
COMMENT ON COLUMN "public"."moon_name_space"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."moon_name_space"."update_time" IS '修改时间';

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."moon_app_id_seq"
OWNED BY "public"."moon_app"."id";
SELECT setval('"public"."moon_app_id_seq"', 7, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."moon_config_id_seq"
OWNED BY "public"."moon_config"."id";
SELECT setval('"public"."moon_config_id_seq"', 16, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."moon_name_space_id_seq"
OWNED BY "public"."moon_name_space"."id";
SELECT setval('"public"."moon_name_space_id_seq"', 3, true);

-- ----------------------------
-- Primary Key structure for table moon_app
-- ----------------------------
ALTER TABLE "public"."moon_app" ADD CONSTRAINT "moon_app_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table moon_config
-- ----------------------------
ALTER TABLE "public"."moon_config" ADD CONSTRAINT "moon_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table moon_name_space
-- ----------------------------
ALTER TABLE "public"."moon_name_space" ADD CONSTRAINT "moon_name_space_pkey" PRIMARY KEY ("id");
