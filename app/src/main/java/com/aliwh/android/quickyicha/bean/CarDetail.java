package com.aliwh.android.quickyicha.bean;

import java.util.List;

/**
 * @author liwh
 * @date 2018/4/27
 * 汽车详情实体类
 */

public class CarDetail {

    /**
     * msg : success
     * result : [{"airConfig":[{"name":"空调控制方式","value":"自动 1"},{"name":"后排独立空调","value":"-"},{"name":"后座出风口","value":"-"},{"name":"温度分区控制","value":"-"},{"name":"车内空气调节/花粉过滤","value":"1"},{"name":"车载冰箱","value":"-"}],"baseInfo":[{"name":"车型名称","value":"奥迪Q5 2016款 40 TFSI 进取型"},{"name":"厂商指导价(元)","value":"38.34万"},{"name":"厂商","value":"一汽-大众奥迪"},{"name":"级别","value":"中型SUV"},{"name":"发动机","value":"2.0T 224马力 L4"},{"name":"变速箱","value":"8挡手自一体"},{"name":"长*宽*高(mm)","value":"4629*1898*1655"},{"name":"车身结构","value":"5门5座SUV"},{"name":"最高车速(km/h)","value":"230"},{"name":"官方0-100km/h加速(s)","value":"7.2"},{"name":"实测0-100km/h加速(s)","value":"-"},{"name":"实测100-0km/h制动(m)","value":"-"},{"name":"实测油耗(L/100km)","value":"-"},{"name":"工信部综合油耗(L/100km)","value":"8.4"},{"name":"实测离地间隙(mm)","value":"-"},{"name":"整车质保","value":"三年或10万公里"}],"brand":"奥迪","brandName":"奥迪Q5","carImage":"http://f2.mob.com/imgs/2016/06/30/WQQ/K52JOWQM6KXU6IRI5P6Q_240x180.jpg","carbody":[{"name":"长度(mm)","value":"4629"},{"name":"宽度(mm)","value":"1898"},{"name":"高度(mm)","value":"1655"},{"name":"轴距(mm)","value":"2807"},{"name":"前轮距(mm)","value":"-"},{"name":"后轮距(mm)","value":"-"},{"name":"最小离地间隙(mm)","value":"185"},{"name":"整备质量(kg)","value":"-"},{"name":"车身结构","value":"SUV"},{"name":"车门数(个)","value":"5"},{"name":"座位数(个)","value":"5"},{"name":"油箱容积(L)","value":"75"},{"name":"行李厢容积(L)","value":"540"}],"chassis":[{"name":"驱动方式","value":"前置四驱"},{"name":"四驱形式","value":"全时四驱"},{"name":"中央差速器结构","value":"托森式差速器"},{"name":"前悬架类型","value":"五连杆式独立悬架"},{"name":"后悬架类型","value":"梯形连杆式独立悬架"},{"name":"助力类型","value":"电动助力"},{"name":"车体结构","value":"承载式"}],"controlConfig":[{"name":"ABS防抱死","value":"1"},{"name":"制动力分配(EBD/CBC等)","value":"1"},{"name":"刹车辅助(EBA/BAS/BA等)","value":"1"},{"name":"牵引力控制(ASR/TCS/TRC等)","value":"1"},{"name":"车身稳定控制(ESC/ESP/DSC等)","value":"1"},{"name":"上坡辅助","value":"1"},{"name":"自动驻车","value":"1"},{"name":"陡坡缓降","value":"1"},{"name":"可变悬架","value":"-"},{"name":"空气悬架","value":"-"},{"name":"可变转向比","value":"-"},{"name":"前桥限滑差速器/差速锁","value":"-"},{"name":"中央差速器锁止功能","value":"-"},{"name":"后桥限滑差速器/差速锁","value":"-"}],"engine":[{"name":"发动机型号","value":"EA888"},{"name":"排量(mL)","value":"1984"},{"name":"排量(L)","value":"2.0"},{"name":"进气形式","value":"涡轮增压"},{"name":"气缸排列形式","value":"L"},{"name":"气缸数(个)","value":"4"},{"name":"每缸气门数(个)","value":"4"},{"name":"压缩比","value":"9.6"},{"name":"配气机构","value":"DOHC"},{"name":"缸径(mm)","value":"-"},{"name":"行程(mm)","value":"-"},{"name":"最大马力(Ps)","value":"224"},{"name":"最大功率(kW)","value":"165"},{"name":"最大功率转速(rpm)","value":"4300-6000"},{"name":"最大扭矩(N·m)","value":"350"},{"name":"最大扭矩转速(rpm)","value":"1500-4200"},{"name":"发动机特有技术","value":"AVS可变气门升程系统"},{"name":"燃料形式","value":"汽油"},{"name":"燃油标号","value":"97号(京95号)"},{"name":"供油方式","value":"混合喷射"},{"name":"缸盖材料","value":"铝"},{"name":"缸体材料","value":"铁"},{"name":"环保标准","value":"国V"}],"exterConfig":[{"name":"电动天窗","value":"1"},{"name":"全景天窗","value":"1"},{"name":"运动外观套件","value":"-"},{"name":"铝合金轮圈","value":"1"},{"name":"电动吸合门","value":"-"},{"name":"侧滑门","value":"-"},{"name":"电动后备厢","value":"1"},{"name":"感应后备厢","value":"-"},{"name":"车顶行李架","value":"1"}],"glassConfig":[{"name":"前/后电动车窗","value":"前 1/后 1"},{"name":"车窗防夹手功能","value":"1"},{"name":"防紫外线/隔热玻璃","value":"1"},{"name":"后视镜电动调节","value":"1"},{"name":"后视镜加热","value":"1"},{"name":"内/外后视镜自动防眩目","value":"-"},{"name":"后视镜电动折叠","value":"-"},{"name":"后视镜记忆","value":"-"},{"name":"后风挡遮阳帘","value":"-"},{"name":"后排侧遮阳帘","value":"-"},{"name":"后排侧隐私玻璃","value":"0"},{"name":"遮阳板化妆镜","value":"1"},{"name":"后雨刷","value":"1"},{"name":"感应雨刷","value":"-"}],"interConfig":[{"name":"真皮方向盘","value":"1"},{"name":"方向盘调节","value":"上下+前后调节"},{"name":"方向盘电动调节","value":"-"},{"name":"多功能方向盘","value":"-"},{"name":"方向盘换挡","value":"-"},{"name":"方向盘加热","value":"-"},{"name":"方向盘记忆","value":"-"},{"name":"定速巡航","value":"1"},{"name":"前/后驻车雷达","value":"前 0/后 1"},{"name":"倒车视频影像","value":"-"},{"name":"行车电脑显示屏","value":"1"},{"name":"全液晶仪表盘","value":"-"},{"name":"HUD抬头数字显示","value":"-"}],"lightConfig":[{"name":"近光灯","value":"氙气"},{"name":"远光灯","value":"氙气"},{"name":"日间行车灯","value":"1"},{"name":"自适应远近光","value":"-"},{"name":"自动头灯","value":"-"},{"name":"转向辅助灯","value":"-"},{"name":"转向头灯","value":"-"},{"name":"前雾灯","value":"1"},{"name":"大灯高度可调","value":"1"},{"name":"大灯清洗装置","value":"1"},{"name":"车内氛围灯","value":"-"}],"mediaConfig":[{"name":"GPS导航系统","value":"-"},{"name":"定位互动服务","value":"-"},{"name":"中控台彩色大屏","value":"1"},{"name":"蓝牙/车载电话","value":"-"},{"name":"车载电视","value":"-"},{"name":"后排液晶屏","value":"-"},{"name":"220V/230V电源","value":"-"},{"name":"外接音源接口","value":"AUX+SD卡插槽"},{"name":"CD支持MP3/WMA","value":"1"},{"name":"多媒体系统","value":"单碟CD(选装多碟CD)"},{"name":"扬声器品牌","value":"-"},{"name":"扬声器数量","value":"10-11喇叭"}],"safetyDevice":[{"name":"主/副驾驶座安全气囊","value":"主 1/副 1"},{"name":"前/后排侧气囊","value":"前 1/后-"},{"name":"前/后排头部气囊(气帘)","value":"前 1/后 1"},{"name":"膝部气囊","value":"-"},{"name":"胎压监测装置","value":"1"},{"name":"零胎压继续行驶","value":"-"},{"name":"安全带未系提示","value":"1"},{"name":"ISOFIX儿童座椅接口","value":"1"},{"name":"发动机电子防盗","value":"1"},{"name":"车内中控锁","value":"1"},{"name":"遥控钥匙","value":"1"},{"name":"无钥匙启动系统","value":"0"},{"name":"无钥匙进入系统","value":"0"}],"seatConfig":[{"name":"座椅材质","value":"织物"},{"name":"运动风格座椅","value":"-"},{"name":"座椅高低调节","value":"1"},{"name":"腰部支撑调节","value":"1"},{"name":"肩部支撑调节","value":"-"},{"name":"主/副驾驶座电动调节","value":"主 1/副 1"},{"name":"第二排靠背角度调节","value":"-"},{"name":"第二排座椅移动","value":"-"},{"name":"后排座椅电动调节","value":"-"},{"name":"电动座椅记忆","value":"-"},{"name":"前/后排座椅加热","value":"-"},{"name":"前/后排座椅通风","value":"-"},{"name":"前/后排座椅按摩","value":"-"},{"name":"第三排座椅","value":"-"},{"name":"后排座椅放倒方式","value":"比例放倒"},{"name":"前/后中央扶手","value":"前 1/后 1"},{"name":"后排杯架","value":"-"}],"seriesName":"奥迪Q5 2016款 40 TFSI 进取型","sonBrand":"一汽-大众奥迪","techConfig":[{"name":"自动泊车入位","value":"-"},{"name":"发动机启停技术","value":"1"},{"name":"并线辅助","value":"-"},{"name":"车道偏离预警系统","value":"-"},{"name":"主动刹车/主动安全系统","value":"-"},{"name":"整体主动转向系统","value":"-"},{"name":"夜视系统","value":"-"},{"name":"中控液晶屏分屏显示","value":"-"},{"name":"自适应巡航","value":"-"},{"name":"全景摄像头","value":"-"}],"transmission":[{"name":"简称","value":"8挡手自一体"},{"name":"挡位个数","value":"8"},{"name":"变速箱类型","value":"手自一体变速箱(AT)"}],"wheelInfo":[{"name":"前制动器类型","value":"通风盘式"},{"name":"后制动器类型","value":"盘式"},{"name":"驻车制动类型","value":"电子驻车"},{"name":"前轮胎规格","value":"235/60 R18"},{"name":"后轮胎规格","value":"235/60 R18"},{"name":"备胎规格","value":"非全尺寸"}]}]
     * retCode : 200
     */

    private String msg;
    private String retCode;
    private List<ResultBean> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * airConfig : [{"name":"空调控制方式","value":"自动 1"},{"name":"后排独立空调","value":"-"},{"name":"后座出风口","value":"-"},{"name":"温度分区控制","value":"-"},{"name":"车内空气调节/花粉过滤","value":"1"},{"name":"车载冰箱","value":"-"}]
         * baseInfo : [{"name":"车型名称","value":"奥迪Q5 2016款 40 TFSI 进取型"},{"name":"厂商指导价(元)","value":"38.34万"},{"name":"厂商","value":"一汽-大众奥迪"},{"name":"级别","value":"中型SUV"},{"name":"发动机","value":"2.0T 224马力 L4"},{"name":"变速箱","value":"8挡手自一体"},{"name":"长*宽*高(mm)","value":"4629*1898*1655"},{"name":"车身结构","value":"5门5座SUV"},{"name":"最高车速(km/h)","value":"230"},{"name":"官方0-100km/h加速(s)","value":"7.2"},{"name":"实测0-100km/h加速(s)","value":"-"},{"name":"实测100-0km/h制动(m)","value":"-"},{"name":"实测油耗(L/100km)","value":"-"},{"name":"工信部综合油耗(L/100km)","value":"8.4"},{"name":"实测离地间隙(mm)","value":"-"},{"name":"整车质保","value":"三年或10万公里"}]
         * brand : 奥迪
         * brandName : 奥迪Q5
         * carImage : http://f2.mob.com/imgs/2016/06/30/WQQ/K52JOWQM6KXU6IRI5P6Q_240x180.jpg
         * carbody : [{"name":"长度(mm)","value":"4629"},{"name":"宽度(mm)","value":"1898"},{"name":"高度(mm)","value":"1655"},{"name":"轴距(mm)","value":"2807"},{"name":"前轮距(mm)","value":"-"},{"name":"后轮距(mm)","value":"-"},{"name":"最小离地间隙(mm)","value":"185"},{"name":"整备质量(kg)","value":"-"},{"name":"车身结构","value":"SUV"},{"name":"车门数(个)","value":"5"},{"name":"座位数(个)","value":"5"},{"name":"油箱容积(L)","value":"75"},{"name":"行李厢容积(L)","value":"540"}]
         * chassis : [{"name":"驱动方式","value":"前置四驱"},{"name":"四驱形式","value":"全时四驱"},{"name":"中央差速器结构","value":"托森式差速器"},{"name":"前悬架类型","value":"五连杆式独立悬架"},{"name":"后悬架类型","value":"梯形连杆式独立悬架"},{"name":"助力类型","value":"电动助力"},{"name":"车体结构","value":"承载式"}]
         * controlConfig : [{"name":"ABS防抱死","value":"1"},{"name":"制动力分配(EBD/CBC等)","value":"1"},{"name":"刹车辅助(EBA/BAS/BA等)","value":"1"},{"name":"牵引力控制(ASR/TCS/TRC等)","value":"1"},{"name":"车身稳定控制(ESC/ESP/DSC等)","value":"1"},{"name":"上坡辅助","value":"1"},{"name":"自动驻车","value":"1"},{"name":"陡坡缓降","value":"1"},{"name":"可变悬架","value":"-"},{"name":"空气悬架","value":"-"},{"name":"可变转向比","value":"-"},{"name":"前桥限滑差速器/差速锁","value":"-"},{"name":"中央差速器锁止功能","value":"-"},{"name":"后桥限滑差速器/差速锁","value":"-"}]
         * engine : [{"name":"发动机型号","value":"EA888"},{"name":"排量(mL)","value":"1984"},{"name":"排量(L)","value":"2.0"},{"name":"进气形式","value":"涡轮增压"},{"name":"气缸排列形式","value":"L"},{"name":"气缸数(个)","value":"4"},{"name":"每缸气门数(个)","value":"4"},{"name":"压缩比","value":"9.6"},{"name":"配气机构","value":"DOHC"},{"name":"缸径(mm)","value":"-"},{"name":"行程(mm)","value":"-"},{"name":"最大马力(Ps)","value":"224"},{"name":"最大功率(kW)","value":"165"},{"name":"最大功率转速(rpm)","value":"4300-6000"},{"name":"最大扭矩(N·m)","value":"350"},{"name":"最大扭矩转速(rpm)","value":"1500-4200"},{"name":"发动机特有技术","value":"AVS可变气门升程系统"},{"name":"燃料形式","value":"汽油"},{"name":"燃油标号","value":"97号(京95号)"},{"name":"供油方式","value":"混合喷射"},{"name":"缸盖材料","value":"铝"},{"name":"缸体材料","value":"铁"},{"name":"环保标准","value":"国V"}]
         * exterConfig : [{"name":"电动天窗","value":"1"},{"name":"全景天窗","value":"1"},{"name":"运动外观套件","value":"-"},{"name":"铝合金轮圈","value":"1"},{"name":"电动吸合门","value":"-"},{"name":"侧滑门","value":"-"},{"name":"电动后备厢","value":"1"},{"name":"感应后备厢","value":"-"},{"name":"车顶行李架","value":"1"}]
         * glassConfig : [{"name":"前/后电动车窗","value":"前 1/后 1"},{"name":"车窗防夹手功能","value":"1"},{"name":"防紫外线/隔热玻璃","value":"1"},{"name":"后视镜电动调节","value":"1"},{"name":"后视镜加热","value":"1"},{"name":"内/外后视镜自动防眩目","value":"-"},{"name":"后视镜电动折叠","value":"-"},{"name":"后视镜记忆","value":"-"},{"name":"后风挡遮阳帘","value":"-"},{"name":"后排侧遮阳帘","value":"-"},{"name":"后排侧隐私玻璃","value":"0"},{"name":"遮阳板化妆镜","value":"1"},{"name":"后雨刷","value":"1"},{"name":"感应雨刷","value":"-"}]
         * interConfig : [{"name":"真皮方向盘","value":"1"},{"name":"方向盘调节","value":"上下+前后调节"},{"name":"方向盘电动调节","value":"-"},{"name":"多功能方向盘","value":"-"},{"name":"方向盘换挡","value":"-"},{"name":"方向盘加热","value":"-"},{"name":"方向盘记忆","value":"-"},{"name":"定速巡航","value":"1"},{"name":"前/后驻车雷达","value":"前 0/后 1"},{"name":"倒车视频影像","value":"-"},{"name":"行车电脑显示屏","value":"1"},{"name":"全液晶仪表盘","value":"-"},{"name":"HUD抬头数字显示","value":"-"}]
         * lightConfig : [{"name":"近光灯","value":"氙气"},{"name":"远光灯","value":"氙气"},{"name":"日间行车灯","value":"1"},{"name":"自适应远近光","value":"-"},{"name":"自动头灯","value":"-"},{"name":"转向辅助灯","value":"-"},{"name":"转向头灯","value":"-"},{"name":"前雾灯","value":"1"},{"name":"大灯高度可调","value":"1"},{"name":"大灯清洗装置","value":"1"},{"name":"车内氛围灯","value":"-"}]
         * mediaConfig : [{"name":"GPS导航系统","value":"-"},{"name":"定位互动服务","value":"-"},{"name":"中控台彩色大屏","value":"1"},{"name":"蓝牙/车载电话","value":"-"},{"name":"车载电视","value":"-"},{"name":"后排液晶屏","value":"-"},{"name":"220V/230V电源","value":"-"},{"name":"外接音源接口","value":"AUX+SD卡插槽"},{"name":"CD支持MP3/WMA","value":"1"},{"name":"多媒体系统","value":"单碟CD(选装多碟CD)"},{"name":"扬声器品牌","value":"-"},{"name":"扬声器数量","value":"10-11喇叭"}]
         * safetyDevice : [{"name":"主/副驾驶座安全气囊","value":"主 1/副 1"},{"name":"前/后排侧气囊","value":"前 1/后-"},{"name":"前/后排头部气囊(气帘)","value":"前 1/后 1"},{"name":"膝部气囊","value":"-"},{"name":"胎压监测装置","value":"1"},{"name":"零胎压继续行驶","value":"-"},{"name":"安全带未系提示","value":"1"},{"name":"ISOFIX儿童座椅接口","value":"1"},{"name":"发动机电子防盗","value":"1"},{"name":"车内中控锁","value":"1"},{"name":"遥控钥匙","value":"1"},{"name":"无钥匙启动系统","value":"0"},{"name":"无钥匙进入系统","value":"0"}]
         * seatConfig : [{"name":"座椅材质","value":"织物"},{"name":"运动风格座椅","value":"-"},{"name":"座椅高低调节","value":"1"},{"name":"腰部支撑调节","value":"1"},{"name":"肩部支撑调节","value":"-"},{"name":"主/副驾驶座电动调节","value":"主 1/副 1"},{"name":"第二排靠背角度调节","value":"-"},{"name":"第二排座椅移动","value":"-"},{"name":"后排座椅电动调节","value":"-"},{"name":"电动座椅记忆","value":"-"},{"name":"前/后排座椅加热","value":"-"},{"name":"前/后排座椅通风","value":"-"},{"name":"前/后排座椅按摩","value":"-"},{"name":"第三排座椅","value":"-"},{"name":"后排座椅放倒方式","value":"比例放倒"},{"name":"前/后中央扶手","value":"前 1/后 1"},{"name":"后排杯架","value":"-"}]
         * seriesName : 奥迪Q5 2016款 40 TFSI 进取型
         * sonBrand : 一汽-大众奥迪
         * techConfig : [{"name":"自动泊车入位","value":"-"},{"name":"发动机启停技术","value":"1"},{"name":"并线辅助","value":"-"},{"name":"车道偏离预警系统","value":"-"},{"name":"主动刹车/主动安全系统","value":"-"},{"name":"整体主动转向系统","value":"-"},{"name":"夜视系统","value":"-"},{"name":"中控液晶屏分屏显示","value":"-"},{"name":"自适应巡航","value":"-"},{"name":"全景摄像头","value":"-"}]
         * transmission : [{"name":"简称","value":"8挡手自一体"},{"name":"挡位个数","value":"8"},{"name":"变速箱类型","value":"手自一体变速箱(AT)"}]
         * wheelInfo : [{"name":"前制动器类型","value":"通风盘式"},{"name":"后制动器类型","value":"盘式"},{"name":"驻车制动类型","value":"电子驻车"},{"name":"前轮胎规格","value":"235/60 R18"},{"name":"后轮胎规格","value":"235/60 R18"},{"name":"备胎规格","value":"非全尺寸"}]
         */

        private String brand;
        private String brandName;
        private String carImage;
        private String seriesName;
        private String sonBrand;
        private List<AirConfigBean> airConfig;
        private List<BaseInfoBean> baseInfo;
        private List<CarbodyBean> carbody;
        private List<ChassisBean> chassis;
        private List<ControlConfigBean> controlConfig;
        private List<EngineBean> engine;
        private List<ExterConfigBean> exterConfig;
        private List<GlassConfigBean> glassConfig;
        private List<InterConfigBean> interConfig;
        private List<LightConfigBean> lightConfig;
        private List<MediaConfigBean> mediaConfig;
        private List<SafetyDeviceBean> safetyDevice;
        private List<SeatConfigBean> seatConfig;
        private List<TechConfigBean> techConfig;
        private List<TransmissionBean> transmission;
        private List<WheelInfoBean> wheelInfo;

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getCarImage() {
            return carImage;
        }

        public void setCarImage(String carImage) {
            this.carImage = carImage;
        }

        public String getSeriesName() {
            return seriesName;
        }

        public void setSeriesName(String seriesName) {
            this.seriesName = seriesName;
        }

        public String getSonBrand() {
            return sonBrand;
        }

        public void setSonBrand(String sonBrand) {
            this.sonBrand = sonBrand;
        }

        public List<AirConfigBean> getAirConfig() {
            return airConfig;
        }

        public void setAirConfig(List<AirConfigBean> airConfig) {
            this.airConfig = airConfig;
        }

        public List<BaseInfoBean> getBaseInfo() {
            return baseInfo;
        }

        public void setBaseInfo(List<BaseInfoBean> baseInfo) {
            this.baseInfo = baseInfo;
        }

        public List<CarbodyBean> getCarbody() {
            return carbody;
        }

        public void setCarbody(List<CarbodyBean> carbody) {
            this.carbody = carbody;
        }

        public List<ChassisBean> getChassis() {
            return chassis;
        }

        public void setChassis(List<ChassisBean> chassis) {
            this.chassis = chassis;
        }

        public List<ControlConfigBean> getControlConfig() {
            return controlConfig;
        }

        public void setControlConfig(List<ControlConfigBean> controlConfig) {
            this.controlConfig = controlConfig;
        }

        public List<EngineBean> getEngine() {
            return engine;
        }

        public void setEngine(List<EngineBean> engine) {
            this.engine = engine;
        }

        public List<ExterConfigBean> getExterConfig() {
            return exterConfig;
        }

        public void setExterConfig(List<ExterConfigBean> exterConfig) {
            this.exterConfig = exterConfig;
        }

        public List<GlassConfigBean> getGlassConfig() {
            return glassConfig;
        }

        public void setGlassConfig(List<GlassConfigBean> glassConfig) {
            this.glassConfig = glassConfig;
        }

        public List<InterConfigBean> getInterConfig() {
            return interConfig;
        }

        public void setInterConfig(List<InterConfigBean> interConfig) {
            this.interConfig = interConfig;
        }

        public List<LightConfigBean> getLightConfig() {
            return lightConfig;
        }

        public void setLightConfig(List<LightConfigBean> lightConfig) {
            this.lightConfig = lightConfig;
        }

        public List<MediaConfigBean> getMediaConfig() {
            return mediaConfig;
        }

        public void setMediaConfig(List<MediaConfigBean> mediaConfig) {
            this.mediaConfig = mediaConfig;
        }

        public List<SafetyDeviceBean> getSafetyDevice() {
            return safetyDevice;
        }

        public void setSafetyDevice(List<SafetyDeviceBean> safetyDevice) {
            this.safetyDevice = safetyDevice;
        }

        public List<SeatConfigBean> getSeatConfig() {
            return seatConfig;
        }

        public void setSeatConfig(List<SeatConfigBean> seatConfig) {
            this.seatConfig = seatConfig;
        }

        public List<TechConfigBean> getTechConfig() {
            return techConfig;
        }

        public void setTechConfig(List<TechConfigBean> techConfig) {
            this.techConfig = techConfig;
        }

        public List<TransmissionBean> getTransmission() {
            return transmission;
        }

        public void setTransmission(List<TransmissionBean> transmission) {
            this.transmission = transmission;
        }

        public List<WheelInfoBean> getWheelInfo() {
            return wheelInfo;
        }

        public void setWheelInfo(List<WheelInfoBean> wheelInfo) {
            this.wheelInfo = wheelInfo;
        }

        public static class AirConfigBean {
            /**
             * name : 空调控制方式
             * value : 自动 1
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class BaseInfoBean {
            /**
             * name : 车型名称
             * value : 奥迪Q5 2016款 40 TFSI 进取型
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class CarbodyBean {
            /**
             * name : 长度(mm)
             * value : 4629
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class ChassisBean {
            /**
             * name : 驱动方式
             * value : 前置四驱
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class ControlConfigBean {
            /**
             * name : ABS防抱死
             * value : 1
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class EngineBean {
            /**
             * name : 发动机型号
             * value : EA888
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class ExterConfigBean {
            /**
             * name : 电动天窗
             * value : 1
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class GlassConfigBean {
            /**
             * name : 前/后电动车窗
             * value : 前 1/后 1
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class InterConfigBean {
            /**
             * name : 真皮方向盘
             * value : 1
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class LightConfigBean {
            /**
             * name : 近光灯
             * value : 氙气
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class MediaConfigBean {
            /**
             * name : GPS导航系统
             * value : -
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class SafetyDeviceBean {
            /**
             * name : 主/副驾驶座安全气囊
             * value : 主 1/副 1
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class SeatConfigBean {
            /**
             * name : 座椅材质
             * value : 织物
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class TechConfigBean {
            /**
             * name : 自动泊车入位
             * value : -
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class TransmissionBean {
            /**
             * name : 简称
             * value : 8挡手自一体
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class WheelInfoBean {
            /**
             * name : 前制动器类型
             * value : 通风盘式
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
