# API Keys Configuration

## ⚠️ IMPORTANT: Never commit actual API keys to Git!

This file shows where to add your API keys for the NewsApp.

---

## 1. OpenAI API Key

**Location**: `app/src/main/java/com/example/newsapp/di/Module.kt`

**Line 78**: Replace `"YOUR_OPENAI_API_KEY_HERE"` with your actual OpenAI API key

```kotlin
fun provideOpenAIApiKey(): String {
    return "sk-proj-YOUR_ACTUAL_KEY_HERE"  // ← Replace this
}
```

**Get your key from**: https://platform.openai.com/api-keys

---

## 2. News API Key

**Location**: `app/src/main/java/com/example/newsapp/model/RetrofitInstance.kt`

**Find**: `const val API_KEY = "YOUR_NEWS_API_KEY_HERE"`

**Replace with**: Your actual News API key

**Get your key from**: https://newsapi.org/

---

## 🔒 Security Best Practices

### For Development:
1. Add your API keys locally (don't commit them)
2. Use environment variables or local.properties
3. Keep keys in a secure password manager

### For Production:
1. Use BuildConfig to inject keys at build time
2. Store keys in CI/CD secrets
3. Use Android Keystore for sensitive data
4. Consider using a backend proxy for API calls

---

## 📝 Example: Using local.properties (Recommended)

### Step 1: Add to `local.properties`:
```properties
OPENAI_API_KEY=sk-proj-your-key-here
NEWS_API_KEY=your-news-api-key-here
```

### Step 2: Update `build.gradle.kts`:
```kotlin
android {
    defaultConfig {
        // Load from local.properties
        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        
        buildConfigField("String", "OPENAI_API_KEY", 
            "\"${properties.getProperty("OPENAI_API_KEY")}\"")
        buildConfigField("String", "NEWS_API_KEY", 
            "\"${properties.getProperty("NEWS_API_KEY")}\"")
    }
}
```

### Step 3: Use in code:
```kotlin
fun provideOpenAIApiKey(): String {
    return BuildConfig.OPENAI_API_KEY
}
```

---

## ✅ Verify `.gitignore` includes:
```
local.properties
*.key
*.keys
```

This ensures your API keys are never accidentally committed to Git.
