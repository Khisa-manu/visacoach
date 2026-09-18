import React, { useState } from 'react';
import { Smartphone, Server, Mic, HelpCircle, ShieldCheck, DollarSign, Terminal, Github, ExternalLink, Sparkles } from 'lucide-react';
import { LiveSimulator } from './components/LiveSimulator';
import { AndroidInteractivePreview } from './components/AndroidInteractivePreview';
import { AndroidArchitectureView } from './components/AndroidArchitectureView';
import { BackendArchitectureView } from './components/BackendArchitectureView';
import { QuestionsRepositoryView } from './components/QuestionsRepositoryView';

export default function App() {
  const [activeTab, setActiveTab] = useState<'PREVIEW' | 'SIMULATOR' | 'ANDROID' | 'BACKEND' | 'QUESTIONS'>('PREVIEW');

  return (
    <div className="min-h-screen bg-slate-50 text-slate-900 flex flex-col font-sans selection:bg-blue-100 selection:text-blue-900">
      {/* Top Brand Navigation Bar */}
      <header className="sticky top-0 z-40 bg-white/95 backdrop-blur border-b border-slate-200">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-16 flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div className="w-9 h-9 bg-[#0A192F] text-white rounded-xl flex items-center justify-center font-black text-base shadow-sm">
              VC
            </div>
            <div>
              <div className="flex items-center gap-2">
                <h1 className="font-bold text-slate-900 text-base leading-tight">USA VisaCoach</h1>
                <span className="text-[10px] uppercase font-bold tracking-wider px-2 py-0.5 bg-teal-50 text-teal-700 rounded-full border border-teal-200">
                  Android &amp; Spring Boot Suite
                </span>
              </div>
              <p className="text-xs text-slate-500">B1/B2 Real-Time Voice Interview Platform • Kenyan Safaricom M-Pesa Integration</p>
            </div>
          </div>

          <div className="hidden md:flex items-center gap-1 bg-slate-100 p-1 rounded-xl text-xs font-semibold text-slate-600">
            <button
              onClick={() => setActiveTab('PREVIEW')}
              className={`flex items-center gap-1.5 px-3 py-1.5 rounded-lg transition-all ${activeTab === 'PREVIEW' ? 'bg-[#0A192F] text-white shadow-sm' : 'hover:text-slate-900'}`}
            >
              <Smartphone className="w-3.5 h-3.5 text-teal-400" />
              Android App Preview
            </button>
            <button
              onClick={() => setActiveTab('SIMULATOR')}
              className={`flex items-center gap-1.5 px-3 py-1.5 rounded-lg transition-all ${activeTab === 'SIMULATOR' ? 'bg-[#0A192F] text-white shadow-sm' : 'hover:text-slate-900'}`}
            >
              <Mic className="w-3.5 h-3.5 text-teal-400" />
              Live Voice Simulator
            </button>
            <button
              onClick={() => setActiveTab('ANDROID')}
              className={`flex items-center gap-1.5 px-3 py-1.5 rounded-lg transition-all ${activeTab === 'ANDROID' ? 'bg-[#0A192F] text-white shadow-sm' : 'hover:text-slate-900'}`}
            >
              <Smartphone className="w-3.5 h-3.5" />
              Android Code (16 Screens)
            </button>
            <button
              onClick={() => setActiveTab('BACKEND')}
              className={`flex items-center gap-1.5 px-3 py-1.5 rounded-lg transition-all ${activeTab === 'BACKEND' ? 'bg-[#0A192F] text-white shadow-sm' : 'hover:text-slate-900'}`}
            >
              <Server className="w-3.5 h-3.5" />
              Spring Boot &amp; MySQL
            </button>
            <button
              onClick={() => setActiveTab('QUESTIONS')}
              className={`flex items-center gap-1.5 px-3 py-1.5 rounded-lg transition-all ${activeTab === 'QUESTIONS' ? 'bg-[#0A192F] text-white shadow-sm' : 'hover:text-slate-900'}`}
            >
              <HelpCircle className="w-3.5 h-3.5" />
              55 B1/B2 Questions
            </button>
          </div>

          <div className="flex items-center gap-2">
            <span className="text-xs font-mono font-medium px-2.5 py-1 bg-emerald-50 text-emerald-700 rounded-lg border border-emerald-200">
              Safaricom Daraja API
            </span>
          </div>
        </div>

        {/* Mobile Navigation Pills */}
        <div className="md:hidden flex items-center justify-around p-2 bg-slate-100 border-t border-slate-200 text-xs font-medium">
          <button
            onClick={() => setActiveTab('PREVIEW')}
            className={`px-2.5 py-1 rounded-lg ${activeTab === 'PREVIEW' ? 'bg-[#0A192F] text-white shadow-sm font-bold' : 'text-slate-600'}`}
          >
            App Preview
          </button>
          <button
            onClick={() => setActiveTab('SIMULATOR')}
            className={`px-2.5 py-1 rounded-lg ${activeTab === 'SIMULATOR' ? 'bg-[#0A192F] text-white shadow-sm font-bold' : 'text-slate-600'}`}
          >
            Simulator
          </button>
          <button
            onClick={() => setActiveTab('ANDROID')}
            className={`px-2.5 py-1 rounded-lg ${activeTab === 'ANDROID' ? 'bg-[#0A192F] text-white shadow-sm font-bold' : 'text-slate-600'}`}
          >
            Android
          </button>
          <button
            onClick={() => setActiveTab('BACKEND')}
            className={`px-2.5 py-1 rounded-lg ${activeTab === 'BACKEND' ? 'bg-[#0A192F] text-white shadow-sm font-bold' : 'text-slate-600'}`}
          >
            Backend
          </button>
          <button
            onClick={() => setActiveTab('QUESTIONS')}
            className={`px-2.5 py-1 rounded-lg ${activeTab === 'QUESTIONS' ? 'bg-[#0A192F] text-white shadow-sm font-bold' : 'text-slate-600'}`}
          >
            Questions
          </button>
        </div>
      </header>

      {/* Main Content Viewport */}
      <main className="flex-1 max-w-7xl w-full mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-8">
        {activeTab === 'PREVIEW' && <AndroidInteractivePreview />}
        {activeTab === 'SIMULATOR' && <LiveSimulator />}
        {activeTab === 'ANDROID' && <AndroidArchitectureView />}
        {activeTab === 'BACKEND' && <BackendArchitectureView />}
        {activeTab === 'QUESTIONS' && <QuestionsRepositoryView />}
      </main>

      {/* Professional Footer */}
      <footer className="border-t border-slate-200 bg-white py-6">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 flex flex-col sm:flex-row items-center justify-between gap-4 text-xs text-slate-500">
          <div>
            <span className="font-semibold text-slate-700">USA VisaCoach</span> — Clean Architecture Android &amp; Spring Boot Suite
          </div>
          <div className="text-center sm:text-right">
            Educational training platform for B1/B2 applicants in Kenya. Not affiliated with the U.S. Department of State.
          </div>
        </div>
      </footer>
    </div>
  );
}
