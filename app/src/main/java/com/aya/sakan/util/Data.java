package com.aya.sakan.util;

import com.aya.sakan.ui.search.Town;

import java.util.ArrayList;
import java.util.List;

public class Data {

    public static List<Town> getTowns() {
        List<Town> townList = new ArrayList<>();
        townList.add(new Town(1, "القاهرة", "Cairo"));
        townList.add(new Town(2, "الجيزة", "Giza"));
        townList.add(new Town(3, "الأسكندرية", "Alexandria"));
        townList.add(new Town(4, "الدقهلية", "Dakahlia"));
        townList.add(new Town(5, "البحر الأحمر", "Red Sea"));
        townList.add(new Town(6, "البحيرة", "Beheira"));
        townList.add(new Town(7, "الفيوم", "Fayoum"));
        townList.add(new Town(8, "الغربية", "Gharbiya"));
        townList.add(new Town(9, "الإسماعلية", "Ismailia"));
        townList.add(new Town(10, "المنوفية", "Monofia"));
        townList.add(new Town(11, "المنيا", "Minya"));
        townList.add(new Town(12, "القليوبية", "Qaliubiya"));
        townList.add(new Town(13, "الوادي الجديد", "New Valley"));
        townList.add(new Town(14, "السويس", "Suez"));
        townList.add(new Town(15, "اسوان", "Aswan"));
        townList.add(new Town(16, "اسيوط", "Assiut"));
        townList.add(new Town(17, "بني سويف", "Beni Suef"));
        townList.add(new Town(18, "بورسعيد", "Port Said"));
        townList.add(new Town(19, "دمياط", "Damietta"));
        townList.add(new Town(20, "الشرقية", "Sharkia"));
        townList.add(new Town(21, "جنوب سيناء", "South Sinai"));
        townList.add(new Town(22, "كفر الشيخ", "Kafr Al sheikh"));
        townList.add(new Town(23, "مطروح", "Matrouh"));
        townList.add(new Town(24, "الأقصر", "Luxor"));
        townList.add(new Town(25, "قنا", "Qena"));
        townList.add(new Town(26, "شمال سيناء", "North Sinai"));
        townList.add(new Town(27, "سوهاج", "Sohag"));

        return townList;
    }

    public static List<Town> getCities(int townId) {
        List<Town> citiesList = new ArrayList<>();

        switch (townId) {
            case 1:
                citiesList.add(new Town(1, "مصر الجديدة", ""));
                citiesList.add(new Town(1, "النزهة", ""));
                citiesList.add(new Town(1, "غرب مدينة نصر", ""));
                citiesList.add(new Town(1, "شرق مدينة نصر", ""));
                citiesList.add(new Town(1, "عين شمس", ""));
                citiesList.add(new Town(1, "حي أول السلام", ""));
                citiesList.add(new Town(1, "السلام ثاني", ""));
                citiesList.add(new Town(1, "المرج", ""));
                citiesList.add(new Town(1, "منشأة ناصر", ""));
                citiesList.add(new Town(1, "المطرية", ""));
                citiesList.add(new Town(1, "غرب القاهرة-حي وسط-", ""));
                citiesList.add(new Town(1, "حي غرب القاهرة", ""));
                citiesList.add(new Town(1, "حي عابدين", ""));
                citiesList.add(new Town(1, "حي بولاق", ""));
                citiesList.add(new Town(1, "", ""));
                citiesList.add(new Town(1, "شبرا", ""));
                citiesList.add(new Town(1, "الزواية الحمراء", ""));
                citiesList.add(new Town(1, "حدائق القبة", ""));
                citiesList.add(new Town(1, "روض الفرج", ""));
                citiesList.add(new Town(1, "الشرابية", ""));
                citiesList.add(new Town(1, "الزيتون", ""));
                citiesList.add(new Town(1, "مصر القديمة", ""));
                citiesList.add(new Town(1, "الخليفة", ""));
                citiesList.add(new Town(1, "المقطم", ""));
                citiesList.add(new Town(1, "البساتين", ""));
                citiesList.add(new Town(1, "دار السلام", ""));
                citiesList.add(new Town(1, "السيدة زينب", ""));
                return citiesList;

            case 2:
                citiesList.add(new Town(2, "الجيزة", "Giza"));
                citiesList.add(new Town(2, "السادس من أكتوبر", "Sixth of October"));
                citiesList.add(new Town(2, "الشيخ زايد", "Cheikh Zayed"));
                citiesList.add(new Town(2, "الحوامدية", "Hawamdiyah"));
                citiesList.add(new Town(2, "البدرشين", "Al Badrasheen"));
                citiesList.add(new Town(2, "الصف", "Saf"));
                citiesList.add(new Town(2, "أطفيح", "Atfih"));
                citiesList.add(new Town(2, "العياط", "Al Ayat"));
                citiesList.add(new Town(2, "الباويطي", "Al-Bawaiti"));
                citiesList.add(new Town(2, "منشأة القناطر", "ManshiyetAl Qanater"));
                citiesList.add(new Town(2, "أوسيم", "Oaseem"));
                citiesList.add(new Town(2, "كرداسة", "Kerdasa"));
                citiesList.add(new Town(2, "أبو النمرس", "Abu Nomros"));
                citiesList.add(new Town(2, "كفر غطاطي", "Kafr Ghati"));
                citiesList.add(new Town(2, "منشأة البكاري", "Manshiyet Al Bakari"));
                return citiesList;

            case 3:
                citiesList.add(new Town(3, "الأسكندرية", "Alexandria"));
                citiesList.add(new Town(3, "برج العرب", "Burj Al Arab"));
                citiesList.add(new Town(3, "برج العرب الجديدة", "New Burj Al Arab"));
                return citiesList;

            case 4:
                citiesList.add(new Town(4, "المنصورة", "Mansoura"));
                citiesList.add(new Town(4, "طلخا", "Talkha"));
                citiesList.add(new Town(4, "ميت غمر", "Mitt Ghamr"));
                citiesList.add(new Town(4, "دكرنس", "Dekernes"));
                citiesList.add(new Town(4, "أجا", "Aga"));
                citiesList.add(new Town(4, "منية النصر", "Menia El Nasr"));
                citiesList.add(new Town(4, "السنبلاوين", "Sinbillawin"));
                citiesList.add(new Town(4, "الكردي", "El Kurdi"));
                citiesList.add(new Town(4, "بني عبيد", "Bani Ubaid"));
                citiesList.add(new Town(4, "المنزلة", "Al Manzala"));
                citiesList.add(new Town(4, "تمي الأمديد", "tami al\"amdid"));
                citiesList.add(new Town(4, "الجمالية", "aljamalia"));
                citiesList.add(new Town(4, "شربين", "Sherbin"));
                citiesList.add(new Town(4, "المطرية", "Mataria"));
                citiesList.add(new Town(4, "بلقاس", "Belqas"));
                citiesList.add(new Town(4, "ميت سلسيل", "Meet Salsil"));
                citiesList.add(new Town(4, "جمصة", "Gamasa"));
                citiesList.add(new Town(4, "محلة دمنة", "Mahalat Damana"));
                citiesList.add(new Town(4, "نبروه", "Nabroh"));
                return citiesList;

            case 5:
                citiesList.add(new Town(5, "الغردقة", "Hurghada"));
                citiesList.add(new Town(5, "رأس غارب", "Ras Ghareb"));
                citiesList.add(new Town(5, "سفاجا", "Safaga"));
                citiesList.add(new Town(5, "القصير", "El Qusiar"));
                citiesList.add(new Town(5, "مرسى علم", "Marsa Alam"));
                citiesList.add(new Town(5, "الشلاتين", "Shalatin"));
                citiesList.add(new Town(5, "حلايب", "Halaib"));
                return citiesList;

            case 6:
                citiesList.add(new Town(6, "دمنهور", "Damanhour"));
                citiesList.add(new Town(6, "كفر الدوار", "Kafr El Dawar"));
                citiesList.add(new Town(6, "رشيد", "Rashid"));
                citiesList.add(new Town(6, "إدكو", "Edco"));
                citiesList.add(new Town(6, "أبو المطامير", "Abu al-Matamir"));
                citiesList.add(new Town(6, "أبو حمص", "Abu Homs"));
                citiesList.add(new Town(6, "الدلنجات", "Delengat"));
                citiesList.add(new Town(6, "المحمودية", "Mahmoudiyah"));
                citiesList.add(new Town(6, "الرحمانية", "Rahmaniyah"));
                citiesList.add(new Town(6, "إيتاي البارود", "Itai Baroud"));
                citiesList.add(new Town(6, "حوش عيسى", "Housh Eissa"));
                citiesList.add(new Town(6, "شبراخيت", "Shubrakhit"));
                citiesList.add(new Town(6, "كوم حمادة", "Kom Hamada"));
                citiesList.add(new Town(6, "بدر", "Badr"));
                citiesList.add(new Town(6, "وادي النطرون", "Wadi Natrun"));
                citiesList.add(new Town(6, "النوبارية الجديدة", "New Nubaria"));
                return citiesList;

            case 7:
                citiesList.add(new Town(7, "الفيوم", "Fayoum"));
                citiesList.add(new Town(7, "الفيوم الجديدة", "Fayoum El Gedida"));
                citiesList.add(new Town(7, "طامية", "Tamiya"));
                citiesList.add(new Town(7, "سنورس", "Snores"));
                citiesList.add(new Town(7, "إطسا", "Etsa"));
                citiesList.add(new Town(7, "إبشواي", "Epschway"));
                citiesList.add(new Town(7, "يوسف الصديق", "Yusuf El Sediaq"));
                return citiesList;

            case 8:
                citiesList.add(new Town(8, "طنطا", "Tanta"));
                citiesList.add(new Town(8, "المحلة الكبرى", "Al Mahalla Al Kobra"));
                citiesList.add(new Town(8, "كفر الزيات", "Kafr El Zayat"));
                citiesList.add(new Town(8, "زفتى", "Zefta"));
                citiesList.add(new Town(8, "السنطة", "El Santa"));
                citiesList.add(new Town(8, "قطور", "Qutour"));
                citiesList.add(new Town(8, "بسيون", "Basion"));
                citiesList.add(new Town(8, "سمنود", "Samannoud"));
                return citiesList;

            case 9:
                citiesList.add(new Town(9, "الإسماعيلية", "Ismailia"));
                citiesList.add(new Town(9, "فايد", "Fayed"));
                citiesList.add(new Town(9, "القنطرة شرق", "Qantara Sharq"));
                citiesList.add(new Town(9, "القنطرة غرب", "Qantara Gharb"));
                citiesList.add(new Town(9, "التل الكبير", "El Tal El Kabier"));
                citiesList.add(new Town(9, "أبو صوير", "Abu Sawir"));
                citiesList.add(new Town(9, "القصاصين الجديدة", "Kasasien El Gedida"));
                return citiesList;

            case 10:
                citiesList.add(new Town(10, "شبين الكوم", "Shbeen El Koom"));
                citiesList.add(new Town(10, "مدينة السادات", "Sadat City"));
                citiesList.add(new Town(10, "منوف", "Menouf"));
                citiesList.add(new Town(10, "سرس الليان", "Sars El-Layan"));
                citiesList.add(new Town(10, "أشمون", "Ashmon"));
                citiesList.add(new Town(10, "الباجور", "Al Bagor"));
                citiesList.add(new Town(10, "قويسنا", "Quesna"));
                citiesList.add(new Town(10, "بركة السبع", "Berkat El Saba"));
                citiesList.add(new Town(10, "تلا", "Tala"));
                citiesList.add(new Town(10, "الشهداء", "Al Shohada"));
                return citiesList;

            case 11:
                citiesList.add(new Town(11, "المنيا", "Minya"));
                citiesList.add(new Town(11, "المنيا الجديدة", "Minya El Gedida"));
                citiesList.add(new Town(11, "العدوة", "El Adwa"));
                citiesList.add(new Town(11, "مغاغة", "Magagha"));
                citiesList.add(new Town(11, "بني مزار", "Bani Mazar"));
                citiesList.add(new Town(11, "مطاي", "Mattay"));
                citiesList.add(new Town(11, "سمالوط", "Samalut"));
                citiesList.add(new Town(11, "المدينة الفكرية", "Madinat El Fekria"));
                citiesList.add(new Town(11, "ملوي", "Meloy"));
                citiesList.add(new Town(11, "دير مواس", "Deir Mawas"));
                return citiesList;

            case 12:
                citiesList.add(new Town(12, "بنها", "Banha"));
                citiesList.add(new Town(12, "قليوب", "Qalyub"));
                citiesList.add(new Town(12, "شبرا الخيمة", "Shubra Al Khaimah"));
                citiesList.add(new Town(12, "القناطر الخيرية", "Al Qanater Charity"));
                citiesList.add(new Town(12, "الخانكة", "Khanka"));
                citiesList.add(new Town(12, "كفر شكر", "Kafr Shukr"));
                citiesList.add(new Town(12, "طوخ", "Tukh"));
                citiesList.add(new Town(12, "قها", "Qaha"));
                citiesList.add(new Town(12, "العبور", "Obour"));
                citiesList.add(new Town(12, "الخصوص", "Khosous"));
                citiesList.add(new Town(12, "شبين القناطر", "Shibin Al Qanater"));
                return citiesList;

            case 13:
                citiesList.add(new Town(13, "الخارجة", "El Kharga"));
                citiesList.add(new Town(13, "باريس", "Paris"));
                citiesList.add(new Town(13, "موط", "Mout"));
                citiesList.add(new Town(13, "الفرافرة", "Farafra"));
                citiesList.add(new Town(13, "بلاط", "Balat"));
                return citiesList;

            case 14:
                citiesList.add(new Town(14, "السويس", "Suez"));
                return citiesList;

            case 15:
                citiesList.add(new Town(15, "أسوان", "Aswan"));
                citiesList.add(new Town(15, "أسوان الجديدة", "Aswan El Gedida"));
                citiesList.add(new Town(15, "دراو", "Drau"));
                citiesList.add(new Town(15, "كوم أمبو", "Kom Ombo"));
                citiesList.add(new Town(15, "نصر النوبة", "Nasr Al Nuba"));
                citiesList.add(new Town(15, "كلابشة", "Kalabsha"));
                citiesList.add(new Town(15, "الرديسية", "Al-Radisiyah"));
                citiesList.add(new Town(15, "البصيلية", "Al Basilia"));
                citiesList.add(new Town(15, "السباعية", "Al Sibaeia"));
                citiesList.add(new Town(15, "ابوسمبل السياحية", "Abo Simbl Al Siyahia"));
                citiesList.add(new Town(15, "إدفو", "Edfu"));
                return citiesList;

            case 16:
                citiesList.add(new Town(16, "أسيوط", "Assiut"));
                citiesList.add(new Town(16, "أسيوط الجديدة", "Assiut El Gedida"));
                citiesList.add(new Town(16, "ديروط", "Dayrout"));
                citiesList.add(new Town(16, "منفلوط", "Manfalut"));
                citiesList.add(new Town(16, "القوصية", "Qusiya"));
                citiesList.add(new Town(16, "أبنوب", "Abnoub"));
                citiesList.add(new Town(16, "أبو تيج", "Abu Tig"));
                citiesList.add(new Town(16, "الغنايم", "El Ghanaim"));
                citiesList.add(new Town(16, "ساحل سليم", "Sahel Selim"));
                citiesList.add(new Town(16, "البداري", "El Badari"));
                citiesList.add(new Town(16, "صدفا", "Sidfa"));
                return citiesList;

            case 17:
                citiesList.add(new Town(17, "بني سويف", "Bani Sweif"));
                citiesList.add(new Town(17, "بني سويف الجديدة", "Beni Suef El Gedida"));
                citiesList.add(new Town(17, "الواسطى", "Al Wasta"));
                citiesList.add(new Town(17, "ناصر", "Naser"));
                citiesList.add(new Town(17, "إهناسيا", "Ehnasia"));
                citiesList.add(new Town(17, "ببا", "beba"));
                citiesList.add(new Town(17, "الفشن", "Fashn"));
                citiesList.add(new Town(17, "سمسطا", "Somasta"));
                return citiesList;

            case 18:
                citiesList.add(new Town(18, "بورسعيد", "PorSaid"));
                citiesList.add(new Town(18, "بورفؤاد", "PorFouad"));
                return citiesList;

            case 19:
                citiesList.add(new Town(19, "دمياط", "Damietta"));
                citiesList.add(new Town(19, "دمياط الجديدة", "New Damietta"));
                citiesList.add(new Town(19, "رأس البر", "Ras El Bar"));
                citiesList.add(new Town(19, "فارسكور", "Faraskour"));
                citiesList.add(new Town(19, "الزرقا", "Zarqa"));
                citiesList.add(new Town(19, "السرو", "alsaru"));
                citiesList.add(new Town(19, "الروضة", "alruwda"));
                citiesList.add(new Town(19, "كفر البطيخ", "Kafr El-Batikh"));
                citiesList.add(new Town(19, "عزبة البرج", "Azbet Al Burg"));
                citiesList.add(new Town(19, "ميت أبو غالب", "Meet Abou Ghalib"));
                citiesList.add(new Town(19, "كفر سعد", "Kafr Saad"));
                return citiesList;

            case 20:
                citiesList.add(new Town(20, "الزقازيق", "Zagazig"));
                citiesList.add(new Town(20, "العاشر من رمضان", "Al Ashr Men Ramadan"));
                citiesList.add(new Town(20, "منيا القمح", "Minya Al Qamh"));
                citiesList.add(new Town(20, "بلبيس", "Belbeis"));
                citiesList.add(new Town(20, "مشتول السوق", "Mashtoul El Souq"));
                citiesList.add(new Town(20, "القنايات", "Qenaiat"));
                citiesList.add(new Town(20, "أبو حماد", "Abu Hammad"));
                citiesList.add(new Town(20, "القرين", "El Qurain"));
                citiesList.add(new Town(20, "ههيا", "Hehia"));
                citiesList.add(new Town(20, "أبو كبير", "Abu Kabir"));
                citiesList.add(new Town(20, "فاقوس", "Faccus"));
                citiesList.add(new Town(20, "الصالحية الجديدة", "El Salihia El Gedida"));
                citiesList.add(new Town(20, "الإبراهيمية", "Al Ibrahimiyah"));
                citiesList.add(new Town(20, "ديرب نجم", "Deirb Negm"));
                citiesList.add(new Town(20, "كفر صقر", "Kafr Saqr"));
                citiesList.add(new Town(20, "أولاد صقر", "Awlad Saqr"));
                citiesList.add(new Town(20, "الحسينية", "Husseiniya"));
                citiesList.add(new Town(20, "صان الحجر القبلية", "san alhajar alqablia"));
                citiesList.add(new Town(20, "منشأة أبو عمر", "Manshayat Abu Omar"));
                return citiesList;

            case 21:
                citiesList.add(new Town(21, "الطور", "Al Toor"));
                citiesList.add(new Town(21, "شرم الشيخ", "Sharm El-Shaikh"));
                citiesList.add(new Town(21, "دهب", "Dahab"));
                citiesList.add(new Town(21, "نويبع", "Nuweiba"));
                citiesList.add(new Town(21, "طابا", "Taba"));
                citiesList.add(new Town(21, "سانت كاترين", "Saint Catherine"));
                citiesList.add(new Town(21, "أبو رديس", "Abu Redis"));
                citiesList.add(new Town(21, "أبو زنيمة", "Abu Zenaima"));
                citiesList.add(new Town(21, "رأس سدر", "Ras Sidr"));
                return citiesList;

            case 22:
                citiesList.add(new Town(22, "كفر الشيخ", "Kafr El Sheikh"));
                citiesList.add(new Town(22, "دسوق", "Desouq"));
                citiesList.add(new Town(22, "فوه", "Fooh"));
                citiesList.add(new Town(22, "مطوبس", "Metobas"));
                citiesList.add(new Town(22, "برج البرلس", "Burg Al Burullus"));
                citiesList.add(new Town(22, "بلطيم", "Baltim"));
                citiesList.add(new Town(22, "مصيف بلطيم", "Masief Baltim"));
                citiesList.add(new Town(22, "الحامول", "Hamol"));
                citiesList.add(new Town(22, "بيلا", "Bella"));
                citiesList.add(new Town(22, "الرياض", "Riyadh"));
                citiesList.add(new Town(22, "سيدي سالم", "Sidi Salm"));
                citiesList.add(new Town(22, "قلين", "Qellen"));
                citiesList.add(new Town(22, "سيدي غازي", "Sidi Ghazi"));
                return citiesList;

            case 23:
                citiesList.add(new Town(23, "مرسى مطروح", "Marsa Matrouh"));
                citiesList.add(new Town(23, "الحمام", "El Hamam"));
                citiesList.add(new Town(23, "العلمين", "Alamein"));
                citiesList.add(new Town(23, "الضبعة", "Dabaa"));
                citiesList.add(new Town(23, "النجيلة", "Al-Nagila"));
                citiesList.add(new Town(23, "سيدي براني", "Sidi Brani"));
                citiesList.add(new Town(23, "السلوم", "Salloum"));
                citiesList.add(new Town(23, "سيوة", "Siwa"));
                return citiesList;

            case 24:
                citiesList.add(new Town(24, "الأقصر", "Luxor"));
                citiesList.add(new Town(24, "الأقصر الجديدة", "New Luxor"));
                citiesList.add(new Town(24, "إسنا", "Esna"));
                citiesList.add(new Town(24, "طيبة الجديدة", "New Tiba"));
                citiesList.add(new Town(24, "الزينية", "Al ziynia"));
                citiesList.add(new Town(24, "البياضية", "Al Bayadieh"));
                citiesList.add(new Town(24, "الطود", "Al Tud"));
                return citiesList;

            case 25:
                citiesList.add(new Town(25, "قنا", "Qena"));
                citiesList.add(new Town(25, "قنا الجديدة", "New Qena"));
                citiesList.add(new Town(25, "أبو تشت", "Abu Tesht"));
                citiesList.add(new Town(25, "نجع حمادي", "Nag Hammadi"));
                citiesList.add(new Town(25, "دشنا", "Deshna"));
                citiesList.add(new Town(25, "الوقف", "Alwaqf"));
                citiesList.add(new Town(25, "قفط", "Qaft"));
                citiesList.add(new Town(25, "نقادة", "Naqada"));
                citiesList.add(new Town(25, "فرشوط", "Farshout"));
                citiesList.add(new Town(25, "قوص", "Quos"));
                return citiesList;

            case 26:
                citiesList.add(new Town(26, "العريش", "Arish"));
                citiesList.add(new Town(26, "الشيخ زويد", "Sheikh Zowaid"));
                citiesList.add(new Town(26, "نخل", "Nakhl"));
                citiesList.add(new Town(26, "رفح", "Rafah"));
                citiesList.add(new Town(26, "بئر العبد", "Bir al-Abed"));
                citiesList.add(new Town(26, "الحسنة", "Al Hasana"));
                return citiesList;

            case 27:
                citiesList.add(new Town(27, "سوهاج", "Sohag"));
                citiesList.add(new Town(27, "سوهاج الجديدة", "Sohag El Gedida"));
                citiesList.add(new Town(27, "أخميم", "Akhmeem"));
                citiesList.add(new Town(27, "أخميم الجديدة", "Akhmim El Gedida"));
                citiesList.add(new Town(27, "البلينا", "Albalina"));
                citiesList.add(new Town(27, "المراغة", "El Maragha"));
                citiesList.add(new Town(27, "المنشأة", "almunsha\"a"));
                citiesList.add(new Town(27, "دار السلام", "Dar AISalaam"));
                citiesList.add(new Town(27, "جرجا", "Gerga"));
                citiesList.add(new Town(27, "جهينة الغربية", "Jahina Al Gharbia"));
                citiesList.add(new Town(27, "ساقلته", "Saqilatuh"));
                citiesList.add(new Town(27, "طما", "Tama"));
                citiesList.add(new Town(27, "طهطا", "Tahta"));
                return citiesList;

            default:
                return citiesList;
        }
    }

    public static List<String> getHomeTypes() {
        List<String> homeTypes = new ArrayList();
        homeTypes.add("شقة");
        homeTypes.add("بيت");
        homeTypes.add("تجاري");
        homeTypes.add("أرض");
        homeTypes.add("أخري");
        return homeTypes;
    }

    public static List<String> getContractType() {
        List<String> contractList = new ArrayList<>();
        contractList.add("بيع");
        contractList.add("ايجار");
        return contractList;
    }


}
