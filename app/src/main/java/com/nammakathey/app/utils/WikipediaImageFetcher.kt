package com.nammakathey.app.utils

object WikipediaImageFetcher {
    /**
     * Predefined Wikipedia image URLs for Karnataka heroes
     * These are public domain images from Wikimedia Commons
     */
    fun getImageUrl(heroId: String): String {
        return when (heroId) {
            // Belagavi
            "sangolli_rayanna" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8f/Sangolli_Rayanna.jpg/220px-Sangolli_Rayanna.jpg"
            "kittur_chennamma" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/4/48/Kittur_Rani_Chennamma.png/220px-Kittur_Rani_Chennamma.png"
            
            // Bijapur
            "adil_shah" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Ibrahim_Adil_Shah_II.jpg/220px-Ibrahim_Adil_Shah_II.jpg"
            "chand_bibi" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1a/Chand_bibi.jpg/220px-Chand_bibi.jpg"
            
            // Belgaum
            "basaveshwara" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f1/Basaveshwara.jpg/220px-Basaveshwara.jpg"
            "akkamahadevi" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2e/Akkamahadevi.jpg/220px-Akkamahadevi.jpg"
            
            // Dharwad
            "jnanpith_awardees" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e3/Kuvempu.jpg/220px-Kuvempu.jpg"
            
            // Hubballi
            "sindhudurg_fort" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/6/67/Sindhudurg_Fort.jpg/220px-Sindhudurg_Fort.jpg"
            
            // Mangaluru
            "narayana_guru" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8f/Narayana_Guru.jpg/220px-Narayana_Guru.jpg"
            "maharshi_dhondo_keshav" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d2/Maharshi_Dhondo_Keshav_Karve.jpg/220px-Maharshi_Dhondo_Keshav_Karve.jpg"
            
            // Mysuru
            "krishnaraja_wodeyar" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Krishnaraja_Wodeyar_IV.jpg/220px-Krishnaraja_Wodeyar_IV.jpg"
            "tipu_sultan" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7a/Tipu_Sultan_Marathi.jpg/220px-Tipu_Sultan_Marathi.jpg"
            
            // Default placeholder (public domain image)
            else -> "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a7/Camponotus_flavomarginatus_ant.jpg/220px-Camponotus_flavomarginatus_ant.jpg"
        }
    }

    /**
     * Get Wikipedia Commons image URL for hero portraits
     * Returns high-quality images suitable for displaying in the app
     */
    fun getHighResImageUrl(heroId: String): String {
        return when (heroId) {
            "sangolli_rayanna" -> "https://upload.wikimedia.org/wikipedia/commons/8/8f/Sangolli_Rayanna.jpg"
            "kittur_chennamma" -> "https://upload.wikimedia.org/wikipedia/commons/4/48/Kittur_Rani_Chennamma.png"
            "adil_shah" -> "https://upload.wikimedia.org/wikipedia/commons/2/2f/Ibrahim_Adil_Shah_II.jpg"
            "chand_bibi" -> "https://upload.wikimedia.org/wikipedia/commons/1/1a/Chand_bibi.jpg"
            "basaveshwara" -> "https://upload.wikimedia.org/wikipedia/commons/f/f1/Basaveshwara.jpg"
            "akkamahadevi" -> "https://upload.wikimedia.org/wikipedia/commons/2/2e/Akkamahadevi.jpg"
            "jnanpith_awardees" -> "https://upload.wikimedia.org/wikipedia/commons/e/e3/Kuvempu.jpg"
            "sindhudurg_fort" -> "https://upload.wikimedia.org/wikipedia/commons/6/67/Sindhudurg_Fort.jpg"
            "narayana_guru" -> "https://upload.wikimedia.org/wikipedia/commons/8/8f/Narayana_Guru.jpg"
            "maharshi_dhondo_keshav" -> "https://upload.wikimedia.org/wikipedia/commons/d/d2/Maharshi_Dhondo_Keshav_Karve.jpg"
            "krishnaraja_wodeyar" -> "https://upload.wikimedia.org/wikipedia/commons/e/e1/Krishnaraja_Wodeyar_IV.jpg"
            "tipu_sultan" -> "https://upload.wikimedia.org/wikipedia/commons/7/7a/Tipu_Sultan_Marathi.jpg"
            else -> getImageUrl(heroId)
        }
    }
}
