import React from 'react';
import { Shield, Sparkles, Smartphone, Award, BookOpen, CheckCircle2, Cpu, Globe, Heart } from 'lucide-react';

export const AboutView: React.FC = () => {
  return (
    <div className="max-w-4xl mx-auto space-y-8">
      {/* Hero Card */}
      <div className="bg-white border border-slate-200 rounded-3xl p-8 shadow-xs relative overflow-hidden">
        <div className="relative z-10 space-y-4">
          <div className="inline-flex items-center gap-2 px-3 py-1 bg-teal-50 text-teal-800 rounded-full text-xs font-semibold border border-teal-200">
            <Sparkles className="w-3.5 h-3.5 text-teal-600" />
            <span>Official Application Overview</span>
          </div>

          <h2 className="text-3xl font-extrabold text-slate-900 tracking-tight">
            USA Visa Interview Guide
          </h2>

          <div className="flex items-center gap-2 text-base font-semibold text-teal-700">
            <span>Developed by Paperglow systems</span>
          </div>

          <p className="text-slate-600 text-sm leading-relaxed max-w-2xl">
            A standalone, 100% offline educational and tactical preparation manual developed by Paperglow systems for applicants attending U.S. nonimmigrant visa interviews (B1/B2, F1, J1, H1B, and visitors) at American embassies and consulates worldwide.
          </p>
        </div>

        {/* Decorative Watermark */}
        <div className="absolute -right-6 -bottom-10 opacity-5 pointer-events-none text-slate-900">
          <Shield className="w-64 h-64" />
        </div>
      </div>

      {/* Developer Overview Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {/* Developer Card */}
        <div className="bg-white border border-slate-200 rounded-2xl p-6 shadow-xs space-y-4">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-xl bg-[#0A2540] text-white flex items-center justify-center font-black text-base shadow-xs">
              PS
            </div>
            <div>
              <h3 className="font-bold text-slate-900 text-base">Paperglow systems</h3>
              <p className="text-xs text-slate-500 font-medium">Software Engineering &amp; Applied Digital Tools</p>
            </div>
          </div>

          <p className="text-xs text-slate-600 leading-relaxed">
            Paperglow systems specializes in building reliable, privacy-centric, offline-first digital products. We believe essential educational tools should run reliably without depending on remote servers, recurring subscriptions, or third-party tracking.
          </p>

          <div className="pt-2 border-t border-slate-100 space-y-2">
            <div className="flex items-center justify-between text-xs">
              <span className="text-slate-500 font-medium">Lead Developer</span>
              <span className="font-semibold text-slate-800">Paperglow systems</span>
            </div>
            <div className="flex items-center justify-between text-xs">
              <span className="text-slate-500 font-medium">Edition</span>
              <span className="font-semibold text-slate-800">v1.0.0 (Static Offline)</span>
            </div>
            <div className="flex items-center justify-between text-xs">
              <span className="text-slate-500 font-medium">Privacy Status</span>
              <span className="font-semibold text-emerald-600">Zero Telemetry • Zero Trackers</span>
            </div>
          </div>
        </div>

        {/* Key Architectural Pillars */}
        <div className="bg-white border border-slate-200 rounded-2xl p-6 shadow-xs space-y-4">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-xl bg-teal-50 text-teal-700 border border-teal-200 flex items-center justify-center font-bold">
              <Shield className="w-5 h-5" />
            </div>
            <div>
              <h3 className="font-bold text-slate-900 text-base">Design Principles</h3>
              <p className="text-xs text-slate-500 font-medium">Why Paperglow systems built this app</p>
            </div>
          </div>

          <div className="space-y-3 text-xs text-slate-600">
            <div className="flex items-start gap-2.5">
              <CheckCircle2 className="w-4 h-4 text-teal-600 shrink-0 mt-0.5" />
              <div>
                <strong className="text-slate-800">Zero Internet Requirement:</strong> Embassy waiting rooms strictly prohibit mobile internet access or electronic accessories. This app functions 100% offline.
              </div>
            </div>
            <div className="flex items-start gap-2.5">
              <CheckCircle2 className="w-4 h-4 text-teal-600 shrink-0 mt-0.5" />
              <div>
                <strong className="text-slate-800">Grounding in Real Immigration Law:</strong> Every answer is aligned with INA Section 214(b) and official Foreign Affairs Manual (FAM) doctrine.
              </div>
            </div>
            <div className="flex items-start gap-2.5">
              <CheckCircle2 className="w-4 h-4 text-teal-600 shrink-0 mt-0.5" />
              <div>
                <strong className="text-slate-800">Instant Performance:</strong> Clean Jetpack Compose Android native codebase with no heavy annotation daemons or runtime crashes.
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Specifications Table */}
      <div className="bg-white border border-slate-200 rounded-2xl p-6 shadow-xs space-y-4">
        <h3 className="font-bold text-base text-slate-900 flex items-center gap-2">
          <Cpu className="w-4 h-4 text-slate-700" />
          Technical &amp; Content Specifications
        </h3>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 text-xs">
          <div className="p-3 bg-slate-50 rounded-xl border border-slate-100">
            <div className="text-slate-400 font-medium">Questions Repository</div>
            <div className="text-lg font-bold text-slate-900 mt-0.5">68 Items</div>
            <div className="text-slate-500 text-[11px] mt-1">Full model answers &amp; red flags</div>
          </div>

          <div className="p-3 bg-slate-50 rounded-xl border border-slate-100">
            <div className="text-slate-400 font-medium">Consular Domains</div>
            <div className="text-lg font-bold text-slate-900 mt-0.5">7 Categories</div>
            <div className="text-slate-500 text-[11px] mt-1">Purpose, Ties, Finances, Traps</div>
          </div>

          <div className="p-3 bg-slate-50 rounded-xl border border-slate-100">
            <div className="text-slate-400 font-medium">Android Stack</div>
            <div className="text-lg font-bold text-slate-900 mt-0.5">Kotlin + Compose</div>
            <div className="text-slate-500 text-[11px] mt-1">Zero daemon locks / Pure Gradle</div>
          </div>

          <div className="p-3 bg-slate-50 rounded-xl border border-slate-100">
            <div className="text-slate-400 font-medium">Distribution</div>
            <div className="text-lg font-bold text-slate-900 mt-0.5">Standalone APK</div>
            <div className="text-slate-500 text-[11px] mt-1">Developed by Paperglow systems</div>
          </div>
        </div>
      </div>

      {/* Disclaimer */}
      <div className="p-4 bg-slate-100 rounded-2xl border border-slate-200 text-xs text-slate-500 leading-relaxed text-center">
        <strong>Notice:</strong> This guide developed by Paperglow systems is for informational and educational preparation purposes only. Consular decisions are made at the sole discretion of U.S. Department of State consular officers in accordance with the Immigration and Nationality Act. No individual outcome is guaranteed.
      </div>
    </div>
  );
};
