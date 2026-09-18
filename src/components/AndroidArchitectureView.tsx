import React, { useState } from 'react';
import { Smartphone, Layers, Database, Cpu, Radio, Shield, Check, Code, FileText } from 'lucide-react';

export const AndroidArchitectureView: React.FC = () => {
  const [activeTab, setActiveTab] = useState<'SCREENS' | 'CLEAN_ARCH' | 'WEBSOCKET' | 'GRADLE' | 'BUILD_APK'>('BUILD_APK');

  const screens = [
    { name: "SplashScreen", desc: "Animated USA VisaCoach splash with auto-routing to authenticated session" },
    { name: "WelcomeScreen", desc: "Hero onboarding highlighting B1/B2 preparation and practice disclaimer" },
    { name: "RegisterScreen", desc: "Kenyan phone number validation (+254 / 07XX) & full legal name entry" },
    { name: "LoginScreen", desc: "Fast OTP-based login with Kenyan phone number" },
    { name: "OtpScreen", desc: "6-digit OTP verification with automatic token persistence in DataStore" },
    { name: "DashboardScreen", desc: "Start Voice Interview CTA, recent mock history, weak areas, and M-Pesa tier status" },
    { name: "ProfileScreen", desc: "Applicant profile form (DS-160 alignment, employer, tenure, funds, prior travel, U.S. ties)" },
    { name: "VisaTypeScreen", desc: "B1/B2 Visitor visa legal criteria under INA Section 214(b) and consular expectations" },
    { name: "InterviewPrepScreen", desc: "Essential checklist: DS-160 confirmation, passport, employment letter, bank statements" },
    { name: "RealTimeInterviewScreen", desc: "Voice state machine (AI SPEAKING, LISTENING, PROCESSING, THINKING), waveform & timer" },
    { name: "InterviewResultsScreen", desc: "6-pillar radar score (Relevance, Clarity, Consistency, Conciseness, etc.) & disclaimer" },
    { name: "PracticeWeakAreasScreen", desc: "Category-focused drills (Travel Purpose, Finances, Home Ties, Accommodation)" },
    { name: "HistoryScreen", desc: "Chronological log of past mock interview scores, transcripts, and recommendations" },
    { name: "SubscriptionScreen", desc: "Free Starter vs VisaCoach Pro (KES 1,499) plan breakdown" },
    { name: "MpesaPaymentScreen", desc: "Safaricom Daraja STK push trigger, PIN prompt instructions, and status polling" },
    { name: "SettingsScreen", desc: "Account preferences, legal notice, audio permissions check, and sign-out" }
  ];

  return (
    <div className="bg-white border border-slate-200 rounded-2xl p-6 shadow-sm">
      <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 pb-5 border-b border-slate-100">
        <div>
          <div className="flex items-center gap-2">
            <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-semibold bg-blue-50 text-blue-700 border border-blue-200">
              Android Module
            </span>
            <span className="text-xs text-slate-500 font-mono">android/app/src/main/</span>
          </div>
          <h2 className="text-xl font-bold text-slate-900 mt-1">Kotlin Jetpack Compose &amp; Clean Architecture</h2>
          <p className="text-sm text-slate-600">Strictly structured in Kotlin with Material 3, MVVM, Hilt, Room, DataStore, and OkHttp WebSocket.</p>
        </div>

        <div className="flex bg-slate-100 p-1 rounded-xl text-xs font-semibold text-slate-600">
          <button
            onClick={() => setActiveTab('SCREENS')}
            className={`px-3 py-1.5 rounded-lg transition-all ${activeTab === 'SCREENS' ? 'bg-white text-slate-900 shadow-sm' : 'hover:text-slate-900'}`}
          >
            16 Screens
          </button>
          <button
            onClick={() => setActiveTab('CLEAN_ARCH')}
            className={`px-3 py-1.5 rounded-lg transition-all ${activeTab === 'CLEAN_ARCH' ? 'bg-white text-slate-900 shadow-sm' : 'hover:text-slate-900'}`}
          >
            Layers &amp; DI
          </button>
          <button
            onClick={() => setActiveTab('WEBSOCKET')}
            className={`px-3 py-1.5 rounded-lg transition-all ${activeTab === 'WEBSOCKET' ? 'bg-white text-slate-900 shadow-sm' : 'hover:text-slate-900'}`}
          >
            Voice Engine
          </button>
          <button
            onClick={() => setActiveTab('GRADLE')}
            className={`px-3 py-1.5 rounded-lg transition-all ${activeTab === 'GRADLE' ? 'bg-white text-slate-900 shadow-sm' : 'hover:text-slate-900'}`}
          >
            Gradle DSL
          </button>
          <button
            onClick={() => setActiveTab('BUILD_APK')}
            className={`px-3 py-1.5 rounded-lg transition-all ${activeTab === 'BUILD_APK' ? 'bg-blue-600 text-white shadow-sm' : 'hover:text-slate-900 text-blue-700 font-bold'}`}
          >
            Build APK Guide
          </button>
        </div>
      </div>

      {activeTab === 'SCREENS' && (
        <div className="py-6">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
            {screens.map((s, idx) => (
              <div key={s.name} className="p-3.5 bg-slate-50 border border-slate-100 rounded-xl hover:border-blue-200 transition-colors">
                <div className="flex items-center justify-between mb-1">
                  <div className="flex items-center gap-2">
                    <span className="w-5 h-5 rounded-full bg-blue-100 text-blue-700 text-xs font-bold flex items-center justify-center">
                      {idx + 1}
                    </span>
                    <span className="text-sm font-bold text-slate-900">{s.name}</span>
                  </div>
                  <span className="text-[10px] font-mono bg-slate-200/60 px-1.5 py-0.5 rounded text-slate-600">Compose</span>
                </div>
                <p className="text-xs text-slate-600 pl-7">{s.desc}</p>
              </div>
            ))}
          </div>
        </div>
      )}

      {activeTab === 'CLEAN_ARCH' && (
        <div className="py-6 space-y-4">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div className="p-4 bg-slate-50 border border-slate-200 rounded-xl">
              <div className="flex items-center gap-2 font-bold text-sm text-slate-900 mb-2">
                <Layers className="w-4 h-4 text-blue-600" />
                Presentation Layer
              </div>
              <ul className="text-xs text-slate-600 space-y-1.5 list-disc list-inside">
                <li><strong>Jetpack Compose:</strong> 100% declarative UI</li>
                <li><strong>Material 3 Theme:</strong> Dynamic color &amp; typography</li>
                <li><strong>Navigation Compose:</strong> Type-safe Screen routes</li>
                <li><strong>StateFlow:</strong> Unidirectional data flow</li>
                <li><strong>ViewModel:</strong> Hilt-injected lifecycle owners</li>
              </ul>
            </div>

            <div className="p-4 bg-slate-50 border border-slate-200 rounded-xl">
              <div className="flex items-center gap-2 font-bold text-sm text-slate-900 mb-2">
                <Cpu className="w-4 h-4 text-emerald-600" />
                Domain Layer
              </div>
              <ul className="text-xs text-slate-600 space-y-1.5 list-disc list-inside">
                <li><strong>Use Cases:</strong> Clean business rules separation</li>
                <li><strong>Domain Models:</strong> Auth, Profile, Evaluation</li>
                <li><strong>Coroutines:</strong> Kotlin asynchronous primitives</li>
                <li><strong>Zero Android Deps:</strong> Pure Kotlin portability</li>
              </ul>
            </div>

            <div className="p-4 bg-slate-50 border border-slate-200 rounded-xl">
              <div className="flex items-center gap-2 font-bold text-sm text-slate-900 mb-2">
                <Database className="w-4 h-4 text-purple-600" />
                Data Layer
              </div>
              <ul className="text-xs text-slate-600 space-y-1.5 list-disc list-inside">
                <li><strong>Retrofit 2 &amp; OkHttp:</strong> Spring REST client</li>
                <li><strong>Room Database:</strong> Local question bank &amp; history</li>
                <li><strong>Preferences DataStore:</strong> Secure JWT store</li>
                <li><strong>OkHttp WebSocket:</strong> Real-time bi-directional audio</li>
              </ul>
            </div>
          </div>

          <div className="p-4 bg-blue-50 border border-blue-200 rounded-xl">
            <div className="flex items-center gap-2 text-sm font-bold text-blue-900 mb-1">
              <Shield className="w-4 h-4 text-blue-700" />
              Security Architecture
            </div>
            <p className="text-xs text-blue-800 leading-relaxed">
              No API keys or sensitive secrets are stored on the Android client. The app connects to Spring Boot via TLS and authenticated JWT tokens stored in encrypted DataStore. The microphone stream is securely recorded in memory and streamed via authenticated WebSocket.
            </p>
          </div>
        </div>
      )}

      {activeTab === 'WEBSOCKET' && (
        <div className="py-6 space-y-4">
          <div className="p-4 bg-slate-900 text-slate-100 rounded-xl font-mono text-xs overflow-x-auto leading-relaxed">
            <span className="text-slate-400">// Client-Server WebSocket Event Contract (Kotlin Data Classes)</span><br/><br/>
            <span className="text-blue-400">enum class</span> <span className="text-emerald-400">AndroidInterviewState</span> &#123;<br/>
            &nbsp;&nbsp;IDLE, CONNECTING, AI_SPEAKING, LISTENING, PROCESSING, AI_THINKING, COMPLETED, ERROR<br/>
            &#125;<br/><br/>
            <span className="text-blue-400">data class</span> <span className="text-emerald-400">ClientWsMessage</span>(<br/>
            &nbsp;&nbsp;<span className="text-purple-300">val</span> event: String, <span className="text-slate-400">// AUTHENTICATE, START_INTERVIEW, AUDIO_CHUNK, AUDIO_END, PING</span><br/>
            &nbsp;&nbsp;<span className="text-purple-300">val</span> token: String? = <span className="text-amber-300">null</span>,<br/>
            &nbsp;&nbsp;<span className="text-purple-300">val</span> audioBase64: String? = <span className="text-amber-300">null</span><br/>
            )<br/><br/>
            <span className="text-blue-400">data class</span> <span className="text-emerald-400">ServerWsMessage</span>(<br/>
            &nbsp;&nbsp;<span className="text-purple-300">val</span> event: String, <span className="text-slate-400">// QUESTION_STARTED, TRANSCRIPT_PARTIAL, TRANSCRIPT_FINAL, INTERVIEW_COMPLETED</span><br/>
            &nbsp;&nbsp;<span className="text-purple-300">val</span> questionText: String?,<br/>
            &nbsp;&nbsp;<span className="text-purple-300">val</span> audioBase64: String?, <span className="text-slate-400">// TTS synthesized voice stream</span><br/>
            &nbsp;&nbsp;<span className="text-purple-300">val</span> category: String?<br/>
            )
          </div>
        </div>
      )}

      {activeTab === 'GRADLE' && (
        <div className="py-6 space-y-4">
          <div className="p-4 bg-slate-900 text-slate-100 rounded-xl font-mono text-xs overflow-x-auto leading-relaxed">
            <span className="text-slate-400">// android/app/build.gradle.kts (Kotlin DSL)</span><br/><br/>
            plugins &#123;<br/>
            &nbsp;&nbsp;id(<span className="text-emerald-300">"com.android.application"</span>)<br/>
            &nbsp;&nbsp;id(<span className="text-emerald-300">"org.jetbrains.kotlin.android"</span>)<br/>
            &nbsp;&nbsp;id(<span className="text-emerald-300">"com.google.dagger.hilt.android"</span>)<br/>
            &nbsp;&nbsp;id(<span className="text-emerald-300">"com.google.devtools.ksp"</span>)<br/>
            &#125;<br/><br/>
            android &#123;<br/>
            &nbsp;&nbsp;namespace = <span className="text-emerald-300">"com.visacoach"</span><br/>
            &nbsp;&nbsp;compileSdk = <span className="text-amber-300">34</span><br/>
            &nbsp;&nbsp;buildFeatures &#123; compose = <span className="text-amber-300">true</span> &#125;<br/>
            &nbsp;&nbsp;compileOptions &#123; sourceCompatibility = JavaVersion.VERSION_17 &#125;<br/>
            &#125;
          </div>
        </div>
      )}
      {activeTab === 'BUILD_APK' && (
        <div className="py-6 space-y-6">
          {/* Steps Overview */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div className="p-4 bg-slate-50 border border-slate-200 rounded-xl">
              <div className="flex items-center gap-2 font-bold text-sm text-slate-900 mb-2">
                <span className="w-5 h-5 rounded-full bg-blue-600 text-white text-xs flex items-center justify-center font-bold">1</span>
                Open Project
              </div>
              <p className="text-xs text-slate-600 leading-relaxed">
                Launch Android Studio and select <strong>Open</strong>. Navigate to and select the <code>android/</code> folder. Wait for Gradle Sync to complete with <strong>JDK 17</strong>.
              </p>
            </div>

            <div className="p-4 bg-slate-50 border border-slate-200 rounded-xl">
              <div className="flex items-center gap-2 font-bold text-sm text-slate-900 mb-2">
                <span className="w-5 h-5 rounded-full bg-blue-600 text-white text-xs flex items-center justify-center font-bold">2</span>
                Build APK
              </div>
              <p className="text-xs text-slate-600 leading-relaxed">
                In top menu: <strong>Build &gt; Build Bundle(s) / APK(s) &gt; Build APK(s)</strong>. Or execute <code>./gradlew assembleDebug</code> in the integrated terminal.
              </p>
            </div>

            <div className="p-4 bg-slate-50 border border-slate-200 rounded-xl">
              <div className="flex items-center gap-2 font-bold text-sm text-slate-900 mb-2">
                <span className="w-5 h-5 rounded-full bg-blue-600 text-white text-xs flex items-center justify-center font-bold">3</span>
                Locate &amp; Install
              </div>
              <p className="text-xs text-slate-600 leading-relaxed">
                Click <strong>locate</strong> in the completion notification or navigate to <code>android/app/build/outputs/apk/debug/app-debug.apk</code> to install via ADB.
              </p>
            </div>
          </div>

          {/* Terminal Commands Card */}
          <div className="p-5 bg-slate-900 text-slate-100 rounded-xl space-y-3 font-mono text-xs">
            <div className="text-slate-400 font-sans font-bold text-sm flex items-center justify-between">
              <span>Terminal Fast-Build Commands</span>
              <span className="text-xs bg-slate-800 text-emerald-400 px-2 py-0.5 rounded font-mono">Gradle 8.4 • JDK 17</span>
            </div>
            <div className="space-y-2">
              <p className="text-slate-400 font-sans text-xs"># Navigate to android directory:</p>
              <div className="p-2.5 bg-slate-950 rounded-lg text-emerald-400 select-all">
                cd android
              </div>

              <p className="text-slate-400 font-sans text-xs mt-3"># Build Debug APK (macOS / Linux):</p>
              <div className="p-2.5 bg-slate-950 rounded-lg text-emerald-400 select-all">
                ./gradlew assembleDebug
              </div>

              <p className="text-slate-400 font-sans text-xs mt-3"># Build Debug APK (Windows PowerShell / CMD):</p>
              <div className="p-2.5 bg-slate-950 rounded-lg text-emerald-400 select-all">
                .\gradlew.bat assembleDebug
              </div>

              <p className="text-slate-400 font-sans text-xs mt-3"># Generated APK Output Location:</p>
              <div className="p-2.5 bg-slate-950 rounded-lg text-blue-300 select-all">
                android/app/build/outputs/apk/debug/app-debug.apk
              </div>

              <p className="text-slate-400 font-sans text-xs mt-3"># Install directly onto plugged phone or emulator:</p>
              <div className="p-2.5 bg-slate-950 rounded-lg text-emerald-400 select-all">
                adb install -r app/build/outputs/apk/debug/app-debug.apk
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};
