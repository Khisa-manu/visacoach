import React, { useState } from 'react';
import { Server, Database, ShieldCheck, DollarSign, Cpu, CheckCircle2, ArrowRight } from 'lucide-react';

export const BackendArchitectureView: React.FC = () => {
  const [activeTab, setActiveTab] = useState<'OVERVIEW' | 'DATABASE' | 'MPESA' | 'AI_ENGINE'>('OVERVIEW');

  return (
    <div className="bg-white border border-slate-200 rounded-2xl p-6 shadow-sm">
      <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 pb-5 border-b border-slate-100">
        <div>
          <div className="flex items-center gap-2">
            <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-semibold bg-purple-50 text-purple-700 border border-purple-200">
              Backend Module
            </span>
            <span className="text-xs text-slate-500 font-mono">backend/src/main/kotlin/</span>
          </div>
          <h2 className="text-xl font-bold text-slate-900 mt-1">Spring Boot WebFlux &amp; MySQL 8+ Engine</h2>
          <p className="text-sm text-slate-600">Enterprise Kotlin backend with Spring Security, Flyway, Redis, and Safaricom Daraja API.</p>
        </div>

        <div className="flex bg-slate-100 p-1 rounded-xl text-xs font-semibold text-slate-600">
          <button
            onClick={() => setActiveTab('OVERVIEW')}
            className={`px-3 py-1.5 rounded-lg transition-all ${activeTab === 'OVERVIEW' ? 'bg-white text-slate-900 shadow-sm' : 'hover:text-slate-900'}`}
          >
            Stack
          </button>
          <button
            onClick={() => setActiveTab('DATABASE')}
            className={`px-3 py-1.5 rounded-lg transition-all ${activeTab === 'DATABASE' ? 'bg-white text-slate-900 shadow-sm' : 'hover:text-slate-900'}`}
          >
            MySQL &amp; Flyway
          </button>
          <button
            onClick={() => setActiveTab('MPESA')}
            className={`px-3 py-1.5 rounded-lg transition-all ${activeTab === 'MPESA' ? 'bg-white text-slate-900 shadow-sm' : 'hover:text-slate-900'}`}
          >
            Daraja M-Pesa
          </button>
          <button
            onClick={() => setActiveTab('AI_ENGINE')}
            className={`px-3 py-1.5 rounded-lg transition-all ${activeTab === 'AI_ENGINE' ? 'bg-white text-slate-900 shadow-sm' : 'hover:text-slate-900'}`}
          >
            AI Orchestrator
          </button>
        </div>
      </div>

      {activeTab === 'OVERVIEW' && (
        <div className="py-6 space-y-4">
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
            <div className="p-4 bg-slate-50 border border-slate-200 rounded-xl">
              <span className="text-xs font-semibold text-slate-500 block mb-1">Framework</span>
              <span className="text-base font-bold text-slate-900 block">Spring Boot 3.2</span>
              <span className="text-xs text-slate-600">Kotlin + WebFlux + Reactive WebSocket</span>
            </div>

            <div className="p-4 bg-slate-50 border border-slate-200 rounded-xl">
              <span className="text-xs font-semibold text-slate-500 block mb-1">Primary Database</span>
              <span className="text-base font-bold text-emerald-700 block">MySQL 8+</span>
              <span className="text-xs text-slate-600">Strictly no Postgres, no MongoDB</span>
            </div>

            <div className="p-4 bg-slate-50 border border-slate-200 rounded-xl">
              <span className="text-xs font-semibold text-slate-500 block mb-1">Payments</span>
              <span className="text-base font-bold text-emerald-600 block">Safaricom Daraja</span>
              <span className="text-xs text-slate-600">M-Pesa Express STK Push only</span>
            </div>

            <div className="p-4 bg-slate-50 border border-slate-200 rounded-xl">
              <span className="text-xs font-semibold text-slate-500 block mb-1">Caching &amp; State</span>
              <span className="text-base font-bold text-red-600 block">Redis Cache</span>
              <span className="text-xs text-slate-600">Live interview session state &amp; OTP</span>
            </div>
          </div>

          <div className="p-4 bg-slate-50 border border-slate-200 rounded-xl">
            <h4 className="text-sm font-bold text-slate-900 mb-2">Architectural Rules Enforced</h4>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-3 text-xs text-slate-700">
              <div className="flex items-center gap-2">
                <CheckCircle2 className="w-4 h-4 text-emerald-600 shrink-0" />
                <span>Zero plain-text passwords or OTPs (BCrypt 12 rounds)</span>
              </div>
              <div className="flex items-center gap-2">
                <CheckCircle2 className="w-4 h-4 text-emerald-600 shrink-0" />
                <span>Audio stored in Cloud Object Storage refs, never inside MySQL</span>
              </div>
              <div className="flex items-center gap-2">
                <CheckCircle2 className="w-4 h-4 text-emerald-600 shrink-0" />
                <span>Backend is sole authority on subscription activation</span>
              </div>
              <div className="flex items-center gap-2">
                <CheckCircle2 className="w-4 h-4 text-emerald-600 shrink-0" />
                <span>Strictly no visa approval or refusal prediction calculations</span>
              </div>
            </div>
          </div>
        </div>
      )}

      {activeTab === 'DATABASE' && (
        <div className="py-6 space-y-4">
          <div className="p-4 bg-slate-900 text-slate-100 rounded-xl font-mono text-xs overflow-x-auto leading-relaxed">
            <span className="text-slate-400">-- Flyway Migrations in backend/src/main/resources/db/migration/</span><br/><br/>
            <span className="text-emerald-400">V1__init_schema.sql</span> &rarr; users, user_profiles, visa_types, questions, interviews, answers, evaluations, feedback, subscription_plans, subscriptions, payments<br/>
            <span className="text-emerald-400">V2__seed_visa_types_and_plans.sql</span> &rarr; Seeds B1/B2 Visa definition &amp; Safaricom M-Pesa KES 1,499 Pro Plan<br/>
            <span className="text-emerald-400">V3__seed_50_b1_b2_questions.sql</span> &rarr; Seeds 55 curated B1/B2 consular interview questions across all 10 categories
          </div>
        </div>
      )}

      {activeTab === 'MPESA' && (
        <div className="py-6 space-y-4">
          <div className="p-4 bg-emerald-50 border border-emerald-200 rounded-xl">
            <div className="flex items-center gap-2 font-bold text-sm text-emerald-900 mb-2">
              <DollarSign className="w-4 h-4 text-emerald-700" />
              Safaricom Daraja STK Push Sequence
            </div>
            <div className="flex flex-col md:flex-row items-center justify-between gap-3 text-xs text-emerald-900 pt-2">
              <div className="p-2.5 bg-white rounded-lg border border-emerald-200 text-center w-full">
                <strong>1. Android App</strong><br/>POST /api/payments/mpesa/stk-push
              </div>
              <ArrowRight className="w-4 h-4 text-emerald-600 shrink-0 hidden md:block" />
              <div className="p-2.5 bg-white rounded-lg border border-emerald-200 text-center w-full">
                <strong>2. Spring Boot</strong><br/>Daraja STK Push &amp; pending record
              </div>
              <ArrowRight className="w-4 h-4 text-emerald-600 shrink-0 hidden md:block" />
              <div className="p-2.5 bg-white rounded-lg border border-emerald-200 text-center w-full">
                <strong>3. User Phone</strong><br/>M-Pesa PIN input dialog
              </div>
              <ArrowRight className="w-4 h-4 text-emerald-600 shrink-0 hidden md:block" />
              <div className="p-2.5 bg-white rounded-lg border border-emerald-200 text-center w-full">
                <strong>4. Webhook Callback</strong><br/>Idempotent MySQL activation
              </div>
            </div>
          </div>
        </div>
      )}

      {activeTab === 'AI_ENGINE' && (
        <div className="py-6 space-y-4">
          <div className="p-4 bg-slate-900 text-slate-100 rounded-xl font-mono text-xs overflow-x-auto leading-relaxed">
            <span className="text-slate-400">// AI Interviewer Strict JSON Contract &amp; Guardrails</span><br/><br/>
            &#123;<br/>
            &nbsp;&nbsp;<span className="text-purple-300">"question"</span>: <span className="text-emerald-300">"What are your primary responsibilities at your job in Nairobi?"</span>,<br/>
            &nbsp;&nbsp;<span className="text-purple-300">"category"</span>: <span className="text-emerald-300">"EMPLOYMENT"</span>,<br/>
            &nbsp;&nbsp;<span className="text-purple-300">"action"</span>: <span className="text-emerald-300">"ASK_NEXT_QUESTION"</span>, <span className="text-slate-400">// ASK_FOLLOW_UP, END_INTERVIEW</span><br/>
            &nbsp;&nbsp;<span className="text-purple-300">"targetAspect"</span>: <span className="text-emerald-300">"Professional tenure and career stability in Kenya"</span><br/>
            &#125;
          </div>
        </div>
      )}
    </div>
  );
};
