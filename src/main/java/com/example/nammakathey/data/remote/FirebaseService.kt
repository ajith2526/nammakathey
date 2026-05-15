package com.example.nammakathey.data.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// --- Data Models ---
data class District(
    val id: String,
    val name: String,
    val nameKn: String,
    val category: String, // Heritage, Coastal, Malnad, Bayaluseeme
    val bannerImage: String, // drawable resource name
    val shortDesc: String,
    val shortDescKn: String,
    val history: String,
    val historyKn: String,
    val famousLandmark: String,
    val famousLandmarkKn: String,
    val landmarkImage: String,
    val cultureSpecialty: String,
    val cultureSpecialtyKn: String,
    val heroId: String? = null
)

data class Hero(
    val id: String,
    val name: String,
    val nameKn: String,
    val districtId: String,
    val heroImage: String,
    val storyPages: List<StoryPage>,
    val quiz: Quiz,
    val statueLocation: String
)

data class StoryPage(val text: String, val textKn: String, val imageUrl: String)
data class Quiz(val questions: List<Question>)
data class Question(val text: String, val textKn: String, val options: List<String>, val correctIndex: Int)

// --- Service / Repository ---
object FirebaseService {
    
    // Expanded list covering major districts with unique identifiers
    private val districts = listOf(
        District(
            id = "mysuru", name = "Mysuru", nameKn = "ಮೈಸೂರು", category = "Heritage",
            bannerImage = "mysuru_banner", shortDesc = "The Cultural Capital", shortDescKn = "ಸಾಂಸ್ಕೃತಿಕ ರಾಜಧಾನಿ",
            history = "Known as the City of Palaces, Mysuru was the capital of the Wodeyar dynasty for centuries.",
            historyKn = "ಮೈಸೂರು ಅರಮನೆಗಳ ನಗರ ಎಂದು ಪ್ರಸಿದ್ಧವಾಗಿದೆ. ಇದು ಶತಮಾನಗಳ ಕಾಲ ಒಡೆಯರ್ ರಾಜವಂಶದ ರಾಜಧಾನಿಯಾಗಿತ್ತು.",
            famousLandmark = "Mysuru Palace", famousLandmarkKn = "ಮೈಸೂರು ಅರಮನೆ", landmarkImage = "mysuru_palace",
            cultureSpecialty = "Mysuru Dasara", cultureSpecialtyKn = "ಮೈಸೂರು ದಸರಾ", heroId = "h5"
        ),
        District(
            id = "ballari", name = "Ballari", nameKn = "ಬಳ್ಳಾರಿ", category = "Heritage",
            bannerImage = "hampi_banner", shortDesc = "Historical Heartland", shortDescKn = "ಐತಿಹಾಸಿಕ ಹೃದಯಭಾಗ",
            history = "Home to the glorious ruins of the Vijayanagara Empire at Hampi, a UNESCO World Heritage site.",
            historyKn = "ವಿಜಯನಗರ ಸಾಮ್ರಾಜ್ಯದ ವೈಭವದ ಅವಶೇಷಗಳಾದ ಹಂಪಿಯ ತವರೂರು.",
            famousLandmark = "Stone Chariot, Hampi", famousLandmarkKn = "ಕಲ್ಲಿನ ರಥ, ಹಂಪಿ", landmarkImage = "hampi_ruins",
            cultureSpecialty = "Hampi Utsava", cultureSpecialtyKn = "ಹಂಪಿ ಉತ್ಸವ", heroId = "h4"
        ),
        District(
            id = "dk", name = "Dakshina Kannada", nameKn = "ದಕ್ಷಿಣ ಕನ್ನಡ", category = "Coastal",
            bannerImage = "dk_banner", shortDesc = "Gateway of Karnataka", shortDescKn = "ಕರ್ನಾಟಕದ ಪ್ರವೇಶ ದ್ವಾರ",
            history = "A coastal hub known for its pristine beaches, ancient temples, and educational excellence.",
            historyKn = "ಸುಂದರ ಸಮುದ್ರತೀರಗಳು ಮತ್ತು ಶಿಕ್ಷಣಕ್ಕೆ ಹೆಸರಾದ ಕರಾವಳಿ ಜಿಲ್ಲೆ.",
            famousLandmark = "Kadri Manjunatha Temple", famousLandmarkKn = "ಕದ್ರಿ ಮಂಜುನಾಥ ದೇವಾಲಯ", landmarkImage = "kadri_temple",
            cultureSpecialty = "Yakshagana", cultureSpecialtyKn = "ಯಕ್ಷಗಾನ", heroId = "h2"
        ),
        District(
            id = "kodagu", name = "Kodagu", nameKn = "ಕೊಡಗು", category = "Malnad",
            bannerImage = "kodagu_banner", shortDesc = "Scotland of India", shortDescKn = "ಭಾರತದ ಸ್ಕಾಟ್ಲೆಂಡ್",
            history = "Land of coffee, hills and brave warriors known as the Kodavas.",
            historyKn = "ಕಾಫಿ, ಬೆಟ್ಟಗಳು ಮತ್ತು ವೀರ ಕೊಡವ ಯೋಧರ ನಾಡು.",
            famousLandmark = "Abbey Falls", famousLandmarkKn = "ಅಬ್ಬೆ ಜಲಪಾತ", landmarkImage = "coorg_hills",
            cultureSpecialty = "Kodava Traditions", cultureSpecialtyKn = "ಕೊಡವ ಸಂಪ್ರದಾಯಗಳು"
        ),
        District(
            id = "shivamogga", name = "Shivamogga", nameKn = "ಶಿವಮೊಗ್ಗ", category = "Malnad",
            bannerImage = "shivamogga_banner", shortDesc = "Nature's Paradise", shortDescKn = "ಪ್ರಕೃತಿಯ ಸ್ವರ್ಗ",
            history = "Gateway to Malnad, home to the majestic Jog Falls and rich literary heritage.",
            historyKn = "ಮಲೆನಾಡಿನ ಹೆಬ್ಬಾಗಿಲು, ಭವ್ಯ ಜೋಗ ಜಲಪಾತದ ತವರೂರು.",
            famousLandmark = "Jog Falls", famousLandmarkKn = "ಜೋಗ ಜಲಪಾತ", landmarkImage = "jog_falls",
            cultureSpecialty = "Malnad Cuisine", cultureSpecialtyKn = "ಮಲೆನಾಡಿನ ಆಹಾರ"
        ),
        District(
            id = "belagavi", name = "Belagavi", nameKn = "ಬೆಳಗಾವಿ", category = "Heritage",
            bannerImage = "belagavi_banner", shortDesc = "The Sugar Bowl", shortDescKn = "ಕರ್ನಾಟಕದ ಸಕ್ಕರೆ ಬಟ್ಟಲು",
            history = "Belagavi is a historical city known for the famous Kittur rebellion led by Rani Chennamma.",
            historyKn = "ಬೆಳಗಾವಿಯು ರಾಣಿ ಚೆನ್ನಮ್ಮ ನೇತೃತ್ವದ ಕಿತ್ತೂರು ದಂಗೆಗೆ ಪ್ರಸಿದ್ಧವಾಗಿದೆ.",
            famousLandmark = "Belagavi Fort", famousLandmarkKn = "ಬೆಳಗಾವಿ ಕೋಟೆ", landmarkImage = "belagavi_fort",
            cultureSpecialty = "Kunda Sweet", cultureSpecialtyKn = "ಕುಂದಾ ಸಿಹಿ", heroId = "h1"
        ),
        District(
            id = "vijayapura", name = "Vijayapura", nameKn = "ವಿಜಯಪುರ", category = "Heritage",
            bannerImage = "vijayapura_banner", shortDesc = "City of Victory", shortDescKn = "ವಿಜಯದ ನಗರ",
            history = "Capital of the Adil Shahi dynasty, famous for the architectural marvel Gol Gumbaz.",
            historyKn = "ಆದಿಲ್ ಶಾಹಿ ರಾಜವಂಶದ ರಾಜಧಾನಿ, ಗೋಲ್ ಗುಮ್ಮಟಕ್ಕೆ ವಿಶ್ವಪ್ರಸಿದ್ಧವಾಗಿದೆ.",
            famousLandmark = "Gol Gumbaz", famousLandmarkKn = "ಗೋಲ್ ಗುಮ್ಮಟ", landmarkImage = "gol_gumbaz",
            cultureSpecialty = "Sufi Music", cultureSpecialtyKn = "ಸೂಫಿ ಸಂಗೀತ", heroId = "h3"
        ),
        District(
            id = "chitradurga", name = "Chitradurga", nameKn = "ಚಿತ್ರದುರ್ಗ", category = "Bayaluseeme",
            bannerImage = "chitradurga_banner", shortDesc = "The Fort City", shortDescKn = "ಕೋಟೆ ನಗರಿ",
            history = "Famous for its impregnable seven-circled stone fort and the legend of Onake Obavva.",
            historyKn = "ಏಳು ಸುತ್ತಿನ ಅಜೇಯ ಕಲ್ಲಿನ ಕೋಟೆ ಮತ್ತು ಓಬವ್ವಳ ವೀರಗಾಥೆಗೆ ಹೆಸರುವಾಸಿಯಾಗಿದೆ.",
            famousLandmark = "Seven-Circuited Fort", famousLandmarkKn = "ಏಳು ಸುತ್ತಿನ ಕೋಟೆ", landmarkImage = "chitradurga_fort",
            cultureSpecialty = "Brave Traditions", cultureSpecialtyKn = "ವೀರ ಸಂಪ್ರದಾಯಗಳು"
        )
    )

    private val heroes = listOf(
        Hero(
            id = "h1", name = "Kittur Chennamma", nameKn = "ಕಿತ್ತೂರು ಚೆನ್ನಮ್ಮ", districtId = "belagavi",
            heroImage = "kittur_chennamma", statueLocation = "Kittur",
            storyPages = listOf(
                StoryPage("Queen of Kittur who fought the British.", "ಬ್ರಿಟಿಷರ ವಿರುದ್ಧ ಹೋರಾಡಿದ ಕಿತ್ತೂರು ರಾಣಿ.", "kittur_chennamma"),
                StoryPage("She led an armed rebellion in 1824.", "ಅವರು 1824 ರಲ್ಲಿ ಶಸ್ತ್ರಾಸ್ತ್ರ ದಂಗೆಯನ್ನು ಮುನ್ನಡೆಸಿದರು.", "kittur_fort")
            ),
            quiz = Quiz(listOf(Question("Who was she?", "ಅವರು ಯಾರು?", listOf("Queen", "Soldier"), 0)))
        ),
        Hero(
            id = "h5", name = "Nalwadi Krishnaraja Wadiyar", nameKn = "ನಾಲ್ವಡಿ ಕೃಷ್ಣರಾಜ ಒಡೆಯರ್", districtId = "mysuru",
            heroImage = "mysuru_maharaja", statueLocation = "Mysuru Palace",
            storyPages = listOf(StoryPage("Architect of Modern Mysore.", "ಆಧುನಿಕ ಮೈಸೂರಿನ ಶಿಲ್ಪಿ.", "mysuru_palace")),
            quiz = Quiz(listOf(Question("Which city did he rule?", "ಅವರು ಯಾವುದನ್ನು ಆಳಿದರು?", listOf("Mysuru", "Hubli"), 0)))
        ),
        Hero(
            id = "h2", name = "Rani Abbakka", nameKn = "ರಾಣಿ ಅಬ್ಬಕ್ಕ", districtId = "dk",
            heroImage = "rani_abbakka", statueLocation = "Ullal",
            storyPages = listOf(StoryPage("First Tuluva Queen of Ullal.", "ಉಳ್ಳಾಲದ ಮೊದಲ ತುಳುವ ರಾಣಿ.", "rani_abbakka")),
            quiz = Quiz(listOf(Question("Where was she from?", "ಅವರು ಎಲ್ಲಿಯವರು?", listOf("Ullal", "Mysore"), 0)))
        ),
        Hero(
            id = "h4", name = "Onake Obavva", nameKn = "ಓಬವ್ವ", districtId = "chitradurga",
            heroImage = "onake_obavva", statueLocation = "Chitradurga Fort",
            storyPages = listOf(StoryPage("Brave woman of Chitradurga.", "ಚಿತ್ರದುರ್ಗದ ವೀರ ಮಹಿಳೆ.", "onake_obavva")),
            quiz = Quiz(listOf(Question("What did she use?", "ಅವರು ಏನನ್ನು ಬಳಸಿದರು?", listOf("Onake", "Sword"), 0)))
        )
    )

    fun getDistricts(): List<District> = districts
    fun getHeroes(): List<Hero> = heroes
    fun getDistrictById(id: String): District? = districts.find { it.id == id }
    fun getHeroById(id: String): Hero? = heroes.find { it.id == id }
    fun getHeroByDistrictId(districtId: String): Hero? = heroes.find { it.districtId == districtId }
}
