import React, { useState } from 'react';
import { 
  ShieldCheck, Smartphone, Copy, Check, ExternalLink, Download, FileText, 
  Sparkles, CheckCircle2, AlertCircle, Layers, Image as ImageIcon, Award, 
  Lock, Terminal, Play, HelpCircle, Star
} from 'lucide-react';

export const PlayStoreKitView: React.FC = () => {
  const [copiedKey, setCopiedKey] = useState<string | null>(null);
  const [activeSubTab, setActiveSubTab] = useState<'LISTING' | 'PRIVACY_POLICY' | 'DATA_SAFETY' | 'RELEASE_BUILD'>('LISTING');

  const handleCopy = (text: string, key: string) => {
    navigator.clipboard.writeText(text);
    setCopiedKey(key);
    setTimeout(() => setCopiedKey(null), 2000);
  };

  const shortDescription = "68 U.S. embassy visa interview questions, model answers & INA 214(b) tactics.";
  
  const fullDescription = `Ace your U.S. Nonimmigrant Visa Interview (B1/B2, F1, J1, H-1B, Transit) with the authoritative offline preparation guide developed by Paperglow systems.

Every year, millions of visa applicants are refused under Section 214(b) of the Immigration and Nationality Act (INA) because they fail to articulate strong ties to their home country or trigger fatal red flags during their 2-to-3 minute interview at the consular window.

The USA Visa Interview Guide equips you with 68 real consular questions, 30-second model answers, and psychological breakdowns of what the consular officer is actually testing.

★ KEY FEATURES:
• 68 REAL EMBASSY QUESTIONS: Verified questions asked by U.S. consular officers worldwide across 7 crucial categories.
• 30-SECOND MODEL ANSWERS: Concise, persuasive answers designed for natural verbal delivery without sounding robotic or rehearsed.
• CONSULAR INTENT EXPLAINED: Understand how officers evaluate you under INA Section 214(b) presumption of immigrant intent.
• FATAL RED FLAGS: High-risk words and statements that trigger immediate refusals—and how to avoid them.
• 100% OFFLINE & STANDALONE: Functions anywhere without internet. Perfect for the embassy waiting room where phones have no cellular service.
• STARRED QUICK REVISION: Bookmark tough questions for rapid review in line on your interview morning.
• ZERO ADS & ZERO TRACKING: Completely private. No accounts, no sign-ups, no tracking.

★ 7 CORE INTERVIEW CATEGORIES:
1. Travel Purpose & Itinerary
2. Employment, Career & Leaves
3. Finances, Sponsorship & Liquid Funding
4. Strong Ties & Rebutting INA 214(b)
5. Family Ties & Relatives in the USA
6. International Travel History & Past Visas
7. High-Risk Traps & Tricky Scenarios

★ ESSENTIAL STRATEGY GUIDES INCLUDED:
- Understanding INA Section 214(b): How to rebut the legal presumption of immigrant intent.
- 10 Golden Rules for the Consular Window: Body language, tone, and delivery.
- Comprehensive Document Checklist: Mandatory vs. supporting evidence.
- Interview Day Walkthrough: From security check-in to passport return.

Engineered with care by Paperglow systems. Designed to build genuine confidence through clarity and knowledge.

DISCLAIMER:
This app is an independent educational tool developed by Paperglow systems for preparation purposes only. Consular decisions are made at the sole discretion of U.S. Department of State consular officers. No visa issuance can be guaranteed.`;

  const privacyPolicyText = `# Privacy Policy for USA Visa Interview Guide
Last Updated: September 18, 2026
Developer: Paperglow systems (support@paperglowsystems.com)

Paperglow systems built the USA Visa Interview Guide app as a Free, Standalone, Offline Reference tool. This SERVICE is provided at no cost and is intended for use as is.

## 1. Zero Data Collection
The USA Visa Interview Guide does not collect, transmit, store, or share any personal, demographic, biometric, or device-identifiable information. 

## 2. Permissions
The application requires ZERO dangerous Android permissions:
- No Internet Permission required for core offline question reference
- No Location Access (GPS/Network)
- No Camera, Microphone, or Audio Recording
- No Contacts, Calendar, or Accounts
- No Storage / File System Access (except standard app-private cache)

## 3. Local Storage & Bookmarks
When you star or bookmark questions for review, this data is saved strictly locally on your device using Android's private SharedPreferences API. This data never leaves your physical device and is automatically deleted if you uninstall the application.

## 4. Third-Party Services
This application does NOT incorporate any third-party SDKs, analytics frameworks (such as Google Analytics or Firebase), advertising networks, or social tracking pixels.

## 5. Children's Privacy
Our Service does not address anyone under the age of 13. We do not knowingly collect personally identifiable information from children under 13.

## 6. Changes to This Privacy Policy
We may update our Privacy Policy from time to time. You are advised to review this page periodically for any changes.

## 7. Contact Us
If you have any questions or suggestions regarding this Privacy Policy, do not hesitate to contact Paperglow systems at support@paperglowsystems.com.`;

  return (
    <div className="space-y-6 max-w-5xl mx-auto">
      {/* Top Banner */}
      <div className="bg-white border border-slate-200 rounded-3xl p-6 sm:p-8 shadow-xs">
        <div className="flex flex-col md:flex-row md:items-center justify-between gap-6">
          <div className="space-y-2">
            <div className="flex items-center gap-2">
              <span className="px-3 py-1 bg-emerald-50 text-emerald-800 rounded-full text-xs font-bold border border-emerald-200 flex items-center gap-1.5">
                <CheckCircle2 className="w-3.5 h-3.5 text-emerald-600" />
                Google Play Console Ready
              </span>
              <span className="text-xs text-slate-500 font-medium">Target SDK 34 • Android 14+</span>
            </div>
            <h2 className="text-2xl sm:text-3xl font-extrabold text-slate-900 tracking-tight">
              Google Play Store Release &amp; Compliance Hub
            </h2>
            <p className="text-sm text-slate-600 max-w-2xl leading-relaxed">
              Complete submission package for publishing to the Google Play Store under <strong>Paperglow systems</strong>. Includes certified store listing copy, 0-permission privacy audit, and Google Play Data Safety compliance.
            </p>
          </div>

          <div className="flex flex-col sm:flex-row gap-2 shrink-0">
            <button
              onClick={() => handleCopy(fullDescription, 'full_desc')}
              className="flex items-center justify-center gap-2 px-4 py-2.5 bg-[#0A2540] text-white text-xs font-bold rounded-xl hover:bg-slate-800 transition-all shadow-xs"
            >
              {copiedKey === 'full_desc' ? (
                <>
                  <Check className="w-4 h-4 text-teal-400" />
                  <span>Description Copied!</span>
                </>
              ) : (
                <>
                  <Copy className="w-4 h-4 text-teal-400" />
                  <span>Copy Store Listing</span>
                </>
              )}
            </button>
          </div>
        </div>

        {/* Sub-tabs */}
        <div className="mt-8 pt-4 border-t border-slate-100 flex items-center gap-2 overflow-x-auto no-scrollbar text-xs font-bold">
          <button
            onClick={() => setActiveSubTab('LISTING')}
            className={`px-3.5 py-2 rounded-xl transition-all ${
              activeSubTab === 'LISTING'
                ? 'bg-[#0A2540] text-white shadow-xs'
                : 'bg-slate-100 text-slate-600 hover:bg-slate-200'
            }`}
          >
            Store Listing Metadata
          </button>
          <button
            onClick={() => setActiveSubTab('PRIVACY_POLICY')}
            className={`px-3.5 py-2 rounded-xl transition-all ${
              activeSubTab === 'PRIVACY_POLICY'
                ? 'bg-[#0A2540] text-white shadow-xs'
                : 'bg-slate-100 text-slate-600 hover:bg-slate-200'
            }`}
          >
            Privacy Policy (Mandatory)
          </button>
          <button
            onClick={() => setActiveSubTab('DATA_SAFETY')}
            className={`px-3.5 py-2 rounded-xl transition-all ${
              activeSubTab === 'DATA_SAFETY'
                ? 'bg-[#0A2540] text-white shadow-xs'
                : 'bg-slate-100 text-slate-600 hover:bg-slate-200'
            }`}
          >
            Data Safety &amp; Audit
          </button>
          <button
            onClick={() => setActiveSubTab('RELEASE_BUILD')}
            className={`px-3.5 py-2 rounded-xl transition-all ${
              activeSubTab === 'RELEASE_BUILD'
                ? 'bg-[#0A2540] text-white shadow-xs'
                : 'bg-slate-100 text-slate-600 hover:bg-slate-200'
            }`}
          >
            AAB / APK Build Commands
          </button>
        </div>
      </div>

      {/* Tab 1: Store Listing Metadata */}
      {activeSubTab === 'LISTING' && (
        <div className="space-y-6">
          {/* Quick Stats Grid */}
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 text-xs">
            <div className="bg-white p-4 rounded-2xl border border-slate-200 shadow-xs space-y-1">
              <span className="text-slate-400 font-medium">App Title (30 Chars max)</span>
              <div className="text-base font-bold text-slate-900">USA Visa Interview Guide</div>
              <span className="text-[11px] text-teal-600 font-semibold">24 / 30 characters</span>
            </div>

            <div className="bg-white p-4 rounded-2xl border border-slate-200 shadow-xs space-y-1">
              <span className="text-slate-400 font-medium">Developer Account</span>
              <div className="text-base font-bold text-slate-900">Paperglow systems</div>
              <span className="text-[11px] text-teal-600 font-semibold">Publisher Verified</span>
            </div>

            <div className="bg-white p-4 rounded-2xl border border-slate-200 shadow-xs space-y-1">
              <span className="text-slate-400 font-medium">Category &amp; Content</span>
              <div className="text-base font-bold text-slate-900">Education / Reference</div>
              <span className="text-[11px] text-emerald-600 font-semibold">Everyone (3+) Rated</span>
            </div>

            <div className="bg-white p-4 rounded-2xl border border-slate-200 shadow-xs space-y-1">
              <span className="text-slate-400 font-medium">Monetization / Ads</span>
              <div className="text-base font-bold text-slate-900">Free • 0 Ads</div>
              <span className="text-[11px] text-emerald-600 font-semibold">No In-App Purchases</span>
            </div>
          </div>

          {/* Short Description Card */}
          <div className="bg-white border border-slate-200 rounded-2xl p-6 shadow-xs space-y-3">
            <div className="flex items-center justify-between">
              <div>
                <h3 className="font-bold text-sm text-slate-900">Short Description (Max 80 Characters)</h3>
                <p className="text-xs text-slate-500">First text seen by users in Google Play Store search results.</p>
              </div>
              <button
                onClick={() => handleCopy(shortDescription, 'short_desc')}
                className="flex items-center gap-1 text-xs font-semibold text-teal-700 hover:text-teal-800 bg-teal-50 px-2.5 py-1.5 rounded-lg border border-teal-200"
              >
                {copiedKey === 'short_desc' ? <Check className="w-3.5 h-3.5" /> : <Copy className="w-3.5 h-3.5" />}
                <span>Copy (78 chars)</span>
              </button>
            </div>
            <div className="p-3.5 bg-slate-50 rounded-xl border border-slate-200 text-xs font-mono text-slate-800">
              {shortDescription}
            </div>
          </div>

          {/* Full Description Card */}
          <div className="bg-white border border-slate-200 rounded-2xl p-6 shadow-xs space-y-3">
            <div className="flex items-center justify-between">
              <div>
                <h3 className="font-bold text-sm text-slate-900">Full Description (Formatted for Google Play)</h3>
                <p className="text-xs text-slate-500">Includes legal disclaimer, INA 214(b) points, and feature highlights.</p>
              </div>
              <button
                onClick={() => handleCopy(fullDescription, 'full_desc_card')}
                className="flex items-center gap-1 text-xs font-semibold text-teal-700 hover:text-teal-800 bg-teal-50 px-2.5 py-1.5 rounded-lg border border-teal-200"
              >
                {copiedKey === 'full_desc_card' ? <Check className="w-3.5 h-3.5" /> : <Copy className="w-3.5 h-3.5" />}
                <span>Copy Full Text</span>
              </button>
            </div>
            <pre className="p-4 bg-slate-50 rounded-xl border border-slate-200 text-xs text-slate-700 font-mono whitespace-pre-wrap max-h-80 overflow-y-auto leading-relaxed">
              {fullDescription}
            </pre>
          </div>

          {/* Graphic Assets Specifications */}
          <div className="bg-white border border-slate-200 rounded-2xl p-6 shadow-xs space-y-4">
            <h3 className="font-bold text-sm text-slate-900 flex items-center gap-2">
              <ImageIcon className="w-4 h-4 text-teal-600" />
              Required Store Graphic Assets
            </h3>

            <div className="grid grid-cols-1 md:grid-cols-3 gap-4 text-xs">
              <div className="p-4 bg-slate-50 rounded-xl border border-slate-200 space-y-2">
                <span className="font-bold text-slate-800 block">1. High-Res App Icon</span>
                <ul className="text-slate-600 space-y-1 text-[11px]">
                  <li>• Dimensions: <strong>512 x 512 px</strong></li>
                  <li>• Format: 32-bit PNG with alpha</li>
                  <li>• Max file size: 1024 KB</li>
                  <li>• Visual: Navy background with "VG" seal</li>
                </ul>
              </div>

              <div className="p-4 bg-slate-50 rounded-xl border border-slate-200 space-y-2">
                <span className="font-bold text-slate-800 block">2. Feature Graphic</span>
                <ul className="text-slate-600 space-y-1 text-[11px]">
                  <li>• Dimensions: <strong>1024 x 500 px</strong></li>
                  <li>• Format: JPEG or 24-bit PNG (no alpha)</li>
                  <li>• Max file size: 15 MB</li>
                  <li>• Visual: Clean banner with mock answers</li>
                </ul>
              </div>

              <div className="p-4 bg-slate-50 rounded-xl border border-slate-200 space-y-2">
                <span className="font-bold text-slate-800 block">3. Screenshots</span>
                <ul className="text-slate-600 space-y-1 text-[11px]">
                  <li>• At least 2 screenshots required (max 8)</li>
                  <li>• 16:9 or 9:16 aspect ratio</li>
                  <li>• Min dimension: 320 px / Max: 3840 px</li>
                  <li>• Capture from Phone Simulator view</li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Tab 2: Privacy Policy */}
      {activeSubTab === 'PRIVACY_POLICY' && (
        <div className="space-y-6">
          <div className="bg-emerald-50 border border-emerald-200 rounded-2xl p-5 flex items-start gap-3">
            <ShieldCheck className="w-5 h-5 text-emerald-700 shrink-0 mt-0.5" />
            <div className="text-xs text-emerald-900 leading-relaxed">
              <strong className="block text-sm font-bold mb-0.5">Google Play Policy Compliant Privacy Declaration</strong>
              Google Play requires every published app to provide a publicly accessible Privacy Policy link, even for 100% offline apps. This policy confirms zero data collection, zero network access, and local SharedPreferences storage only.
            </div>
          </div>

          <div className="bg-white border border-slate-200 rounded-2xl p-6 shadow-xs space-y-4">
            <div className="flex items-center justify-between">
              <div>
                <h3 className="font-bold text-sm text-slate-900">Official Privacy Policy for Paperglow systems</h3>
                <p className="text-xs text-slate-500">Host this on GitHub Pages, your website, or Google Sites for your Play Console URL.</p>
              </div>
              <button
                onClick={() => handleCopy(privacyPolicyText, 'privacy_policy')}
                className="flex items-center gap-1 text-xs font-semibold text-teal-700 hover:text-teal-800 bg-teal-50 px-3 py-1.5 rounded-lg border border-teal-200"
              >
                {copiedKey === 'privacy_policy' ? <Check className="w-3.5 h-3.5" /> : <Copy className="w-3.5 h-3.5" />}
                <span>Copy Markdown</span>
              </button>
            </div>

            <div className="p-4 bg-slate-50 rounded-xl border border-slate-200 text-xs text-slate-700 font-mono whitespace-pre-wrap leading-relaxed max-h-96 overflow-y-auto">
              {privacyPolicyText}
            </div>
          </div>
        </div>
      )}

      {/* Tab 3: Data Safety */}
      {activeSubTab === 'DATA_SAFETY' && (
        <div className="bg-white border border-slate-200 rounded-2xl p-6 shadow-xs space-y-6">
          <div>
            <h3 className="font-bold text-base text-slate-900">Google Play Console Data Safety Questionnaire Answers</h3>
            <p className="text-xs text-slate-500 mt-1">Exact answers to select when filling out the Google Play Data Safety form:</p>
          </div>

          <div className="space-y-4 text-xs">
            <div className="p-4 rounded-xl border border-slate-200 bg-slate-50 space-y-2">
              <div className="flex items-center justify-between">
                <span className="font-bold text-slate-900">Does your app collect or share any user data?</span>
                <span className="px-2.5 py-0.5 bg-emerald-100 text-emerald-800 font-bold rounded">NO</span>
              </div>
              <p className="text-slate-600 text-[11px]">
                The app operates entirely locally. Questions and guides are pre-bundled in the APK. Starred questions are persisted in local private SharedPreferences and are never transmitted over the internet.
              </p>
            </div>

            <div className="p-4 rounded-xl border border-slate-200 bg-slate-50 space-y-2">
              <div className="flex items-center justify-between">
                <span className="font-bold text-slate-900">Is all user data collected by your app encrypted in transit?</span>
                <span className="px-2.5 py-0.5 bg-slate-200 text-slate-800 font-bold rounded">N/A (No data transmitted)</span>
              </div>
              <p className="text-slate-600 text-[11px]">
                Because zero network calls or telemetry events occur, there is no transit transmission.
              </p>
            </div>

            <div className="p-4 rounded-xl border border-slate-200 bg-slate-50 space-y-2">
              <div className="flex items-center justify-between">
                <span className="font-bold text-slate-900">Does your app provide a way for users to request data deletion?</span>
                <span className="px-2.5 py-0.5 bg-emerald-100 text-emerald-800 font-bold rounded">YES (Clear Data / Uninstall)</span>
              </div>
              <p className="text-slate-600 text-[11px]">
                Users can clear bookmarks at any time via the UI, or wipe app data from standard Android Settings &gt; Apps &gt; Storage.
              </p>
            </div>

            <div className="p-4 rounded-xl border border-slate-200 bg-slate-50 space-y-2">
              <div className="flex items-center justify-between">
                <span className="font-bold text-slate-900">Target Age &amp; Children's Online Privacy (COPPA)</span>
                <span className="px-2.5 py-0.5 bg-sky-100 text-sky-800 font-bold rounded">Ages 13 and above</span>
              </div>
              <p className="text-slate-600 text-[11px]">
                Select 13-17 and 18+ target audiences. App does not appeal to children under 13.
              </p>
            </div>
          </div>
        </div>
      )}

      {/* Tab 4: Release Build */}
      {activeSubTab === 'RELEASE_BUILD' && (
        <div className="bg-white border border-slate-200 rounded-2xl p-6 shadow-xs space-y-6">
          <div>
            <h3 className="font-bold text-base text-slate-900">Building Google Play Android App Bundle (.aab)</h3>
            <p className="text-xs text-slate-500 mt-1">Google Play requires the modern <strong>Android App Bundle (.aab)</strong> format rather than raw APKs for all new app submissions.</p>
          </div>

          <div className="space-y-4">
            <div className="p-4 bg-slate-900 text-slate-100 rounded-xl space-y-2 font-mono text-xs">
              <div className="text-slate-400 font-sans font-bold flex items-center gap-1.5">
                <Terminal className="w-4 h-4 text-teal-400" />
                Command to Generate Release Bundle (.aab)
              </div>
              <pre className="bg-black/50 p-3 rounded-lg overflow-x-auto text-emerald-400">
{`# 1. Navigate to the android folder
cd android

# 2. Build the production App Bundle (Windows PowerShell)
.\\gradlew.bat bundleRelease

# Or on Mac / Linux:
./gradlew bundleRelease`}
              </pre>
            </div>

            <div className="p-4 bg-slate-50 rounded-xl border border-slate-200 text-xs space-y-2">
              <strong className="block text-slate-900 font-bold">Bundle Output Location:</strong>
              <div className="font-mono bg-white p-2.5 rounded-lg border border-slate-200 text-slate-800">
                android/app/build/outputs/bundle/release/app-release.aab
              </div>
              <p className="text-slate-600 text-[11px]">
                Upload this file directly into the Google Play Console under <strong>Production &gt; Create new release</strong>.
              </p>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};
