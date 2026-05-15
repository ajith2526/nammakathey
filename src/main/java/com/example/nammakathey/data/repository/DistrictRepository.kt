package com.example.nammakathey.data.repository

import com.example.nammakathey.data.models.*

object DistrictRepository {
    private val allDistricts = listOf(
        createDistrict("bagalkot", "Bagalkot", "ಬಾಗಲಕೋಟೆ", "https://images.unsplash.com/photo-1623343831301-382a39282902?q=80&w=1000", "Basavanna", "ಬಸವಣ್ಣ"),
        createDistrict("ballari", "Ballari", "ಬಳ್ಳಾರಿ", "https://images.unsplash.com/photo-1590050752117-23a9d7fc20c3?q=80&w=1000", "Kumararama", "ಕುಮಾರರಾಮ"),
        createDistrict("belagavi", "Belagavi", "ಬೆಳಗಾವಿ", "https://images.unsplash.com/photo-1627814420800-47b678c430e6?q=80&w=1000", "Kittur Chennamma", "ಕಿತ್ತೂರು ಚೆನ್ನಮ್ಮ"),
        createDistrict("bengaluru_rural", "Bengaluru Rural", "ಬೆಂಗಳೂರು ಗ್ರಾಮಾಂತರ", "https://images.unsplash.com/photo-1596402184320-417d717867cd?q=80&w=1000", "Kempe Gowda", "ಕೆಂಪೇಗೌಡ"),
        createDistrict("bengaluru_urban", "Bengaluru Urban", "ಬೆಂಗಳೂರು ನಗರ", "https://images.unsplash.com/photo-1596402184320-417d717867cd?q=80&w=1000", "Kempe Gowda", "ಕೆಂಪೇಗೌಡ"),
        createDistrict("bidar", "Bidar", "ಬೀದರ್", "https://images.unsplash.com/photo-1623343831301-382a39282902?q=80&w=1000", "Bahmani Sultans", "ಬಹಮನಿ ಸುಲ್ತಾನರು"),
        createDistrict("chamarajanagar", "Chamarajanagar", "ಚಾಮರಾಜನಗರ", "https://images.unsplash.com/photo-1624546597799-a918a296306e?q=80&w=1000", "Male Mahadeshwara", "ಮಲೆ ಮಹದೇಶ್ವರ"),
        createDistrict("chikkaballapur", "Chikkaballapur", "ಚಿಕ್ಕಬಳ್ಳಾಪುರ", "https://images.unsplash.com/photo-1632314546440-2099307223f0?q=80&w=1000", "Sir M. Visvesvaraya", "ಸರ್ ಎಂ. ವಿಶ್ವೇಶ್ವರಯ್ಯ"),
        createDistrict("chikkamagaluru", "Chikkamagaluru", "ಚಿಕ್ಕಮಗಳೂರು", "https://images.unsplash.com/photo-1624546597799-a918a296306e?q=80&w=1000", "Kuvempu", "ಕುವೆಂಪು"),
        createDistrict("chitradurga", "Chitradurga", "ಚಿತ್ರದುರ್ಗ", "https://images.unsplash.com/photo-1623343831301-382a39282902?q=80&w=1000", "Onake Obavva", "ಓನಕೆ ಓಬವ್ವ"),
        createDistrict("dakshina_kannada", "Dakshina Kannada", "ದಕ್ಷಿಣ ಕನ್ನಡ", "https://images.unsplash.com/photo-1635384266187-f8cc00684f5c?q=80&w=1000", "Rani Abbakka", "ರಾಣಿ ಅಬ್ಬಕ್ಕ"),
        createDistrict("davanagere", "Davanagere", "ದಾವಣಗೆರೆ", "https://images.unsplash.com/photo-1623343831301-382a39282902?q=80&w=1000", "Davanagere Hero", "ದಾವಣಗೆರೆ ವೀರ"),
        createDistrict("dharwad", "Dharwad", "ಧಾರವಾಡ", "https://images.unsplash.com/photo-1623343831301-382a39282902?q=80&w=1000", "Da Ra Bendre", "ದ ರಾ ಬೇಂದ್ರೆ"),
        createDistrict("gadag", "Gadag", "ಗದಗ", "https://images.unsplash.com/photo-1623343831301-382a39282902?q=80&w=1000", "Kumaravyasa", "ಕುಮಾರವ್ಯಾಸ"),
        createDistrict("hassan", "Hassan", "ಹಾಸನ", "https://images.unsplash.com/photo-1624546597799-a918a296306e?q=80&w=1000", "Hoysala Kings", "ಹೊಯ್ಸಳ ರಾಜರು"),
        createDistrict("haveri", "Haveri", "ಹಾವೇರಿ", "https://images.unsplash.com/photo-1623343831301-382a39282902?q=80&w=1000", "Santa Shishunala Sharifa", "ಸಂತ ಶಿಶುನಾಳ ಶರೀಫ"),
        createDistrict("kalaburagi", "Kalaburagi", "ಕಲಬುರಗಿ", "https://images.unsplash.com/photo-1623343831301-382a39282902?q=80&w=1000", "Khwaja Bande Nawaz", "ಖ್ವಾಜಾ ಬಂದೆ ನವಾಜ್"),
        createDistrict("kodagu", "Kodagu", "ಕೊಡಗು", "https://images.unsplash.com/photo-1624546597799-a918a296306e?q=80&w=1000", "Field Marshal Cariappa", "ಫೀಲ್ಡ್ ಮಾರ್ಷಲ್ ಕಾರ್ಯಪ್ಪ"),
        createDistrict("kolar", "Kolar", "ಕೋಲಾರ", "https://images.unsplash.com/photo-1623343831301-382a39282902?q=80&w=1000", "Kolar Hero", "ಕೋಲಾರ ವೀರ"),
        createDistrict("koppal", "Koppal", "ಕೊಪ್ಪಳ", "https://images.unsplash.com/photo-1623343831301-382a39282902?q=80&w=1000", "Koppal Hero", "ಕೊಪ್ಪಳ ವೀರ"),
        createDistrict("mandya", "Mandya", "ಮಂಡ್ಯ", "https://images.unsplash.com/photo-1590050752117-23a9d7fc20c3?q=80&w=1000", "Tipu Sultan", "ಟಿಪ್ಪು ಸುಲ್ತಾನ್"),
        createDistrict("mysuru", "Mysuru", "ಮೈಸೂರು", "https://images.unsplash.com/photo-1626014303706-53d9e3222c16?q=80&w=1000", "Nalwadi Krishnaraja Wadiyar", "ನಾಲ್ವಡಿ ಕೃಷ್ಣರಾಜ ಒಡೆಯರ್"),
        createDistrict("raichur", "Raichur", "ರಾಯಚೂರು", "https://images.unsplash.com/photo-1623343831301-382a39282902?q=80&w=1000", "Raichur Hero", "ರಾಯಚೂರು ವೀರ"),
        createDistrict("ramanagara", "Ramanagara", "ರಾಮನಗರ", "https://images.unsplash.com/photo-1624546597799-a918a296306e?q=80&w=1000", "Ramanagara Hero", "ರಾಮನಗರ ವೀರ"),
        createDistrict("shivamogga", "Shivamogga", "ಶಿವಮೊಗ್ಗ", "https://images.unsplash.com/photo-1624546597799-a918a296306e?q=80&w=1000", "Keladi Chennamma", "ಕೆಳದಿ ಚೆನ್ನಮ್ಮ"),
        createDistrict("tumakuru", "Tumakuru", "ತುಮಕೂರು", "https://images.unsplash.com/photo-1623343831301-382a39282902?q=80&w=1000", "Sree Sree Shivakumara Swamiji", "ಶ್ರೀ ಶ್ರೀ ಶಿವಕುಮಾರ ಸ್ವಾಮೀಜಿ"),
        createDistrict("udupi", "Udupi", "ಉಡುಪಿ", "https://images.unsplash.com/photo-1623343831301-382a39282902?q=80&w=1000", "Madhvacharya", "ಮಧ್ವಾಚಾರ್ಯ"),
        createDistrict("uttara_kannada", "Uttara Kannada", "ಉತ್ತರ ಕನ್ನಡ", "https://images.unsplash.com/photo-1624546597799-a918a296306e?q=80&w=1000", "Uttara Kannada Hero", "ಉತ್ತರ ಕನ್ನಡ ವೀರ"),
        createDistrict("vijayapura", "Vijayapura", "ವಿಜಯಪುರ", "https://images.unsplash.com/photo-1588665715874-987820b8f60b?q=80&w=1000", "Ali Adil Shah", "ಅಲಿ ಆದಿಲ್ ಶಾ"),
        createDistrict("yadgir", "Yadgir", "ಯಾದಗಿರಿ", "https://images.unsplash.com/photo-1623343831301-382a39282902?q=80&w=1000", "Yadgir Hero", "ಯಾದಗಿರಿ ವೀರ"),
        createDistrict("vijayanagara", "Vijayanagara", "ವಿಜಯನಗರ", "https://images.unsplash.com/photo-1590050752117-23a9d7fc20c3?q=80&w=1000", "Krishnadevaraya", "ಕೃಷ್ಣದೇವರಾಯ")
    )

    fun getAllDistricts() = allDistricts

    fun getDistrictById(id: String) = allDistricts.find { it.id == id }

    fun searchDistricts(query: String, isEnglish: Boolean): List<District> {
        if (query.isEmpty()) return allDistricts
        return allDistricts.filter {
            if (isEnglish) {
                it.name.contains(query, ignoreCase = true)
            } else {
                it.kannadaName.contains(query)
            }
        }
    }

    private fun createDistrict(id: String, name: String, knName: String, imageUrl: String, heroName: String, knHeroName: String): District {
        return District(
            id = id,
            name = name,
            kannadaName = knName,
            imageUrl = imageUrl,
            color = 0xFFFFFFFF, // Not used anymore as we use images
            hero = Hero(
                name = heroName,
                kannadaName = knHeroName,
                imageUrl = imageUrl,
                location = name,
                kannadaLocation = knName,
                historyPages = listOf(
                    StoryPage(
                        title = "History of $name",
                        kannadaTitle = "$knName ದ ಇತಿಹಾಸ",
                        content = "Learn about the rich culture and heritage of $name district and the life of $heroName.",
                        kannadaContent = "$knName ಜಿಲ್ಲೆಯ ಶ್ರೀಮಂತ ಸಂಸ್ಕೃತಿ ಮತ್ತು ಪರಂಪರೆ ಹಾಗೂ $knHeroName ರ ಜೀವನದ ಬಗ್ಗೆ ತಿಳಿಯಿರಿ.",
                        imageUrl = imageUrl
                    ),
                    StoryPage(
                        title = "Heroic Deeds",
                        kannadaTitle = "ವೀರ ಕಾರ್ಯಗಳು",
                        content = "$heroName is remembered for their significant contributions to the region and the state of Karnataka.",
                        kannadaContent = "$knHeroName ಅವರು ಈ ಪ್ರದೇಶಕ್ಕೆ ಮತ್ತು ಕರ್ನಾಟಕ ರಾಜ್ಯಕ್ಕೆ ನೀಡಿದ ಮಹತ್ವದ ಕೊಡುಗೆಗಳಿಗಾಗಿ ಸ್ಮರಿಸಲ್ಪಡುತ್ತಾರೆ.",
                        imageUrl = imageUrl
                    )
                ),
                quizQuestions = listOf(
                    QuizQuestion(
                        question = "Which hero is associated with $name?",
                        kannadaQuestion = "$knName ಗೆ ಸಂಬಂಧಿಸಿದ ವೀರ ಯಾರು?",
                        options = listOf(heroName, "Another Hero", "Random Name", "Unknown"),
                        kannadaOptions = listOf(knHeroName, "ಇನ್ನೊಬ್ಬ ವೀರ", "ಯಾವುದೋ ಹೆಸರು", "ಗೊತ್ತಿಲ್ಲ"),
                        correctOptionIndex = 0
                    ),
                    QuizQuestion(
                        question = "What is the primary location of $heroName?",
                        kannadaQuestion = "$knHeroName ರ ಮುಖ್ಯ ಸ್ಥಳ ಯಾವುದು?",
                        options = listOf(name, "Bengaluru", "Mysuru", "Hubballi"),
                        kannadaOptions = listOf(knName, "ಬೆಂಗಳೂರು", "ಮೈಸೂರು", "ಹುಬ್ಬಳ್ಳಿ"),
                        correctOptionIndex = 0
                    )
                )
            )
        )
    }
}
