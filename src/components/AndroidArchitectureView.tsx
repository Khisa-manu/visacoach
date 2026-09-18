import React, { useState } from 'react';
import { Smartphone, Layers, Check, Code, FileText, Terminal, Shield, Zap, Sparkles, Database } from 'lucide-react';

export const AndroidArchitectureView: React.FC = () => {
  const [activeTab, setActiveTab] = useState<'OVERVIEW' | 'BUILD_GUIDE' | 'SOURCE_CODE'>('BUILD_GUIDE');

  return (
    <div className="bg-white border border-slate-200 rounded-2xl p-6 shadow-xs space-y-6">
      {/* Header */}
      <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 pb-5 border-b border-slate-100">
        <div>
          <div className="flex items-center gap-2">
            <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-semibold bg-teal-50 text-teal-700 border border-teal-200">
              Static Android Architecture
            </span>
            <span className="text-xs text-slate-500 font-mono">android/app/src/main/</span>
          </div>
          <h2 className="text-xl font-bold text-slate-900 mt-1">
            Jetpack Compose • 100% Offline • Zero Daemon Collisions
          </h2>
          <p className="text-sm text-slate-600">
            Simplified, robust Android app that requires no server, no AI model runtime, and no payment gateways.
          </p>
        </div>

        <div className="flex bg-slate-100 p-1 rounded-xl text-xs font-semibold text-slate-600">
          <button
            onClick={() => setActiveTab('BUILD_GUIDE')}
            className={`px-3 py-1.5 rounded-lg transition-all ${activeTab === 'BUILD_GUIDE' ? 'bg-white text-slate-900 shadow-xs font-bold' : 'hover:text-slate-900'}`}
          >
            Build in Android Studio
          </button>
          <button
            onClick={() => setActiveTab('OVERVIEW')}
            className={`px-3 py-1.5 rounded-lg transition-all ${activeTab === 'OVERVIEW' ? 'bg-white text-slate-900 shadow-xs font-bold' : 'hover:text-slate-900'}`}
          >
            Architecture Summary
          </button>
          <button
            onClick={() => setActiveTab('SOURCE_CODE')}
            className={`px-3 py-1.5 rounded-lg transition-all ${activeTab === 'SOURCE_CODE' ? 'bg-white text-slate-900 shadow-xs font-bold' : 'hover:text-slate-900'}`}
          >
            Kotlin Source Code
          </button>
        </div>
      </div>

      {activeTab === 'BUILD_GUIDE' && (
        <div className="space-y-6">
          <div className="bg-emerald-50/80 border border-emerald-200 rounded-xl p-4">
            <h3 className="font-bold text-sm text-emerald-900 flex items-center gap-2">
              <Check className="w-4 h-4 text-emerald-600" />
              Build Issues Permanently Resolved
            </h3>
            <p className="text-xs text-emerald-800 mt-1 leading-relaxed">
              By removing complex annotation processors (Hilt/KSP) and heavy network SDKs (Retrofit, WebSocket, Datastore), the Android project now compiles cleanly with standard Gradle without OOM crashes or Kotlin daemon lock issues.
            </p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div className="p-4 bg-slate-50 rounded-xl border border-slate-200 space-y-2">
              <h4 className="font-bold text-xs text-slate-900 flex items-center gap-1.5">
                <Terminal className="w-4 h-4 text-slate-700" />
                1. Build Debug APK via Command Line
              </h4>
              <p className="text-xs text-slate-600">Open terminal inside the <code className="font-mono bg-slate-200 px-1 py-0.5 rounded text-[11px]">android/</code> folder:</p>
              <pre className="bg-slate-900 text-slate-100 p-3 rounded-lg text-xs font-mono overflow-x-auto">
{`# On Windows PowerShell / Command Prompt:
cd android
.\\gradlew.bat assembleDebug

# Or to run unit tests:
.\\gradlew.bat test`}
              </pre>
            </div>

            <div className="p-4 bg-slate-50 rounded-xl border border-slate-200 space-y-2">
              <h4 className="font-bold text-xs text-slate-900 flex items-center gap-1.5">
                <Smartphone className="w-4 h-4 text-slate-700" />
                2. Generated Output Location
              </h4>
              <p className="text-xs text-slate-600">The compiled APK is placed directly in:</p>
              <pre className="bg-slate-900 text-emerald-400 p-3 rounded-lg text-xs font-mono overflow-x-auto">
                android/app/build/outputs/apk/debug/app-debug.apk
              </pre>
              <p className="text-[11px] text-slate-500">
                You can drag and drop this APK into an Android emulator or install it directly onto any physical phone.
              </p>
            </div>
          </div>

          <div className="p-4 bg-white rounded-xl border border-slate-200 space-y-2">
            <h4 className="font-bold text-xs text-slate-900">How to Open in Android Studio</h4>
            <ol className="list-decimal list-inside space-y-1.5 text-xs text-slate-600">
              <li>Launch <strong>Android Studio</strong>.</li>
              <li>Select <strong>File &gt; Open...</strong> and choose the <code>android</code> subfolder of this repository.</li>
              <li>Wait 30-45 seconds for Gradle to sync dependencies.</li>
              <li>Click <strong>Run (Shift+F10)</strong> with your phone or emulator selected.</li>
            </ol>
          </div>
        </div>
      )}

      {activeTab === 'OVERVIEW' && (
        <div className="space-y-4">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div className="p-4 rounded-xl border border-slate-200 bg-slate-50/50 space-y-1">
              <div className="text-xs font-bold uppercase tracking-wider text-teal-600">Data Layer</div>
              <h4 className="font-bold text-sm text-slate-900">VisaQuestionsData.kt</h4>
              <p className="text-xs text-slate-600 leading-relaxed">
                68 structured questions across 7 consular categories. Includes model answers, consular intent, and red flag traps.
              </p>
            </div>

            <div className="p-4 rounded-xl border border-slate-200 bg-slate-50/50 space-y-1">
              <div className="text-xs font-bold uppercase tracking-wider text-teal-600">UI Layer</div>
              <h4 className="font-bold text-sm text-slate-900">MainActivity.kt</h4>
              <p className="text-xs text-slate-600 leading-relaxed">
                Declarative Jetpack Compose with Material 3 navigation bar: Questions, Embassy Guides, and Saved Bookmarks.
              </p>
            </div>

            <div className="p-4 rounded-xl border border-slate-200 bg-slate-50/50 space-y-1">
              <div className="text-xs font-bold uppercase tracking-wider text-teal-600">Persistence</div>
              <h4 className="font-bold text-sm text-slate-900">SharedPreferences</h4>
              <p className="text-xs text-slate-600 leading-relaxed">
                Lightweight, instant local persistence for starred questions with zero database locking overhead.
              </p>
            </div>
          </div>

          <div className="p-4 rounded-xl border border-slate-200 bg-teal-50/40 flex flex-col sm:flex-row items-start sm:items-center justify-between gap-3 text-xs">
            <div>
              <span className="font-bold text-slate-900">Developed by Paperglow systems</span>
              <p className="text-slate-600 mt-0.5">
                Engineered with clean architectural separation for reliable, zero-server offline utility.
              </p>
            </div>
            <span className="px-2.5 py-1 bg-white text-teal-800 font-semibold rounded-lg border border-teal-200 shadow-2xs shrink-0">
              v1.0 Offline Native
            </span>
          </div>
        </div>
      )}

      {activeTab === 'SOURCE_CODE' && (
        <div className="space-y-4">
          <div className="flex items-center justify-between text-xs text-slate-500">
            <span className="font-mono">android/app/src/main/java/com/visacoach/data/VisaQuestionsData.kt</span>
            <span className="text-teal-600 font-semibold">Clean Kotlin Repository</span>
          </div>
          <pre className="bg-slate-950 text-slate-100 p-4 rounded-xl text-xs font-mono overflow-x-auto max-h-96">
{`package com.visacoach.data

enum class QuestionCategory(val id: String, val title: String) {
    ALL("ALL", "All Questions"),
    PURPOSE("PURPOSE", "Travel Purpose"),
    EMPLOYMENT("EMPLOYMENT", "Employment & Career"),
    FINANCES("FINANCES", "Finances & Funding"),
    TIES_214B("TIES_214B", "Ties & Section 214(b)"),
    FAMILY_RELATIVES("FAMILY_RELATIVES", "Family & U.S. Relatives"),
    TRAVEL_HISTORY("TRAVEL_HISTORY", "Travel History & Prior Visas"),
    RED_FLAGS("RED_FLAGS", "Tricky Traps & Red Flags")
}

data class VisaQuestionItem(
    val id: String,
    val category: QuestionCategory,
    val question: String,
    val shortSummary: String,
    val officerIntent: String,
    val sampleAnswer: String,
    val redFlags: List<String>,
    val tips: List<String>
)

object VisaQuestionsRepository {
    val questions: List<VisaQuestionItem> = listOf( /* 68 questions */ )
    val guides: List<VisaGuideItem> = listOf( /* 4 guides */ )

    fun searchQuestions(query: String, category: QuestionCategory = QuestionCategory.ALL): List<VisaQuestionItem> {
        // Fast instant in-memory filtering
    }
}`}
          </pre>
        </div>
      )}
    </div>
  );
};
