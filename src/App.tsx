import React, { useState } from 'react';
import { Smartphone, BookOpen, HelpCircle, Code, Shield, Sparkles, Info } from 'lucide-react';
import { AndroidInteractivePreview } from './components/AndroidInteractivePreview';
import { QuestionsRepositoryView } from './components/QuestionsRepositoryView';
import { GuidesView } from './components/GuidesView';
import { AndroidArchitectureView } from './components/AndroidArchitectureView';
import { AboutView } from './components/AboutView';

export default function App() {
  const [activeTab, setActiveTab] = useState<'MOBILE_PREVIEW' | 'QUESTIONS_CATALOG' | 'GUIDES' | 'ANDROID_CODE' | 'ABOUT'>('MOBILE_PREVIEW');

  return (
    <div className="min-h-screen bg-slate-50 text-slate-900 flex flex-col font-sans selection:bg-teal-100 selection:text-teal-900">
      {/* Top Navigation Bar */}
      <header className="sticky top-0 z-40 bg-white/95 backdrop-blur border-b border-slate-200">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-16 flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div className="w-9 h-9 bg-[#0A2540] text-white rounded-xl flex items-center justify-center font-black text-sm shadow-xs tracking-tight">
              VG
            </div>
            <div>
              <div className="flex items-center gap-2">
                <h1 className="font-bold text-slate-900 text-base leading-tight">USA Visa Interview Guide</h1>
                <span className="text-[10px] uppercase font-bold tracking-wider px-2 py-0.5 bg-teal-50 text-teal-700 rounded-full border border-teal-200">
                  Static • 100% Offline
                </span>
              </div>
              <p className="text-xs text-slate-500">
                Developed by Paperglow systems • 68 Questions, Model Answers &amp; INA 214(b) Decoded
              </p>
            </div>
          </div>

          {/* Desktop Tab Switcher */}
          <div className="hidden md:flex items-center gap-1 bg-slate-100 p-1 rounded-xl text-xs font-semibold text-slate-600">
            <button
              onClick={() => setActiveTab('MOBILE_PREVIEW')}
              className={`flex items-center gap-1.5 px-3 py-1.5 rounded-lg transition-all ${
                activeTab === 'MOBILE_PREVIEW' ? 'bg-[#0A2540] text-white shadow-xs' : 'hover:text-slate-900'
              }`}
            >
              <Smartphone className="w-3.5 h-3.5 text-teal-400" />
              Mobile App Simulator
            </button>
            <button
              onClick={() => setActiveTab('QUESTIONS_CATALOG')}
              className={`flex items-center gap-1.5 px-3 py-1.5 rounded-lg transition-all ${
                activeTab === 'QUESTIONS_CATALOG' ? 'bg-[#0A2540] text-white shadow-xs' : 'hover:text-slate-900'
              }`}
            >
              <HelpCircle className="w-3.5 h-3.5 text-teal-400" />
              68 Questions &amp; Answers
            </button>
            <button
              onClick={() => setActiveTab('GUIDES')}
              className={`flex items-center gap-1.5 px-3 py-1.5 rounded-lg transition-all ${
                activeTab === 'GUIDES' ? 'bg-[#0A2540] text-white shadow-xs' : 'hover:text-slate-900'
              }`}
            >
              <BookOpen className="w-3.5 h-3.5 text-teal-400" />
              Strategy Guides
            </button>
            <button
              onClick={() => setActiveTab('ANDROID_CODE')}
              className={`flex items-center gap-1.5 px-3 py-1.5 rounded-lg transition-all ${
                activeTab === 'ANDROID_CODE' ? 'bg-[#0A2540] text-white shadow-xs' : 'hover:text-slate-900'
              }`}
            >
              <Code className="w-3.5 h-3.5 text-teal-400" />
              Android Studio Code
            </button>
            <button
              onClick={() => setActiveTab('ABOUT')}
              className={`flex items-center gap-1.5 px-3 py-1.5 rounded-lg transition-all ${
                activeTab === 'ABOUT' ? 'bg-[#0A2540] text-white shadow-xs' : 'hover:text-slate-900'
              }`}
            >
              <Info className="w-3.5 h-3.5 text-teal-400" />
              About
            </button>
          </div>

          {/* Offline Badge & Credits */}
          <div className="hidden sm:flex items-center gap-1.5 px-2.5 py-1 bg-slate-100 text-slate-700 rounded-lg text-xs font-medium border border-slate-200">
            <Shield className="w-3.5 h-3.5 text-teal-600" />
            <span>Paperglow systems</span>
          </div>
        </div>

        {/* Mobile Navigation Tabs */}
        <div className="md:hidden flex items-center justify-around p-2 bg-slate-100 border-t border-slate-200 text-xs font-medium overflow-x-auto no-scrollbar">
          <button
            onClick={() => setActiveTab('MOBILE_PREVIEW')}
            className={`px-2 py-1 rounded-lg shrink-0 ${activeTab === 'MOBILE_PREVIEW' ? 'bg-[#0A2540] text-white font-bold' : 'text-slate-600'}`}
          >
            Preview
          </button>
          <button
            onClick={() => setActiveTab('QUESTIONS_CATALOG')}
            className={`px-2 py-1 rounded-lg shrink-0 ${activeTab === 'QUESTIONS_CATALOG' ? 'bg-[#0A2540] text-white font-bold' : 'text-slate-600'}`}
          >
            68 Questions
          </button>
          <button
            onClick={() => setActiveTab('GUIDES')}
            className={`px-2 py-1 rounded-lg shrink-0 ${activeTab === 'GUIDES' ? 'bg-[#0A2540] text-white font-bold' : 'text-slate-600'}`}
          >
            Guides
          </button>
          <button
            onClick={() => setActiveTab('ANDROID_CODE')}
            className={`px-2 py-1 rounded-lg shrink-0 ${activeTab === 'ANDROID_CODE' ? 'bg-[#0A2540] text-white font-bold' : 'text-slate-600'}`}
          >
            Android
          </button>
          <button
            onClick={() => setActiveTab('ABOUT')}
            className={`px-2 py-1 rounded-lg shrink-0 ${activeTab === 'ABOUT' ? 'bg-[#0A2540] text-white font-bold' : 'text-slate-600'}`}
          >
            About
          </button>
        </div>
      </header>

      {/* Main Content Area */}
      <main className="flex-1 max-w-7xl w-full mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {activeTab === 'MOBILE_PREVIEW' && <AndroidInteractivePreview onOpenAbout={() => setActiveTab('ABOUT')} />}
        {activeTab === 'QUESTIONS_CATALOG' && <QuestionsRepositoryView />}
        {activeTab === 'GUIDES' && <GuidesView />}
        {activeTab === 'ANDROID_CODE' && <AndroidArchitectureView />}
        {activeTab === 'ABOUT' && <AboutView />}
      </main>

      {/* Footer */}
      <footer className="border-t border-slate-200 bg-white py-6 mt-auto">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 flex flex-col sm:flex-row items-center justify-between gap-4 text-xs text-slate-500">
          <div className="flex items-center gap-2">
            <span className="font-semibold text-slate-700">USA Visa Interview Guide</span>
            <span>•</span>
            <span className="text-slate-600 font-medium">Developed by Paperglow systems</span>
          </div>
          <div className="text-center sm:text-right">
            Educational guide based on U.S. Immigration and Nationality Act (INA 214(b)) and official Foreign Affairs Manual (FAM).
          </div>
        </div>
      </footer>
    </div>
  );
}
