import React, { useState, useMemo } from 'react';
import { 
  HelpCircle, Tag, Search, CheckCircle2, AlertTriangle, Info, Copy, Check, 
  ChevronDown, ChevronUp, Printer, Download, Sparkles
} from 'lucide-react';
import { VISA_CATEGORIES, VISA_QUESTIONS, VisaQuestion } from '../data/visaQuestions';

export const QuestionsRepositoryView: React.FC = () => {
  const [selectedCategory, setSelectedCategory] = useState<string>('ALL');
  const [searchQuery, setSearchQuery] = useState<string>('');
  const [expandedIds, setExpandedIds] = useState<Set<string>>(new Set(['tp-01', 'tie-01']));
  const [copiedId, setCopiedId] = useState<string | null>(null);

  const toggleExpand = (id: string) => {
    setExpandedIds(prev => {
      const next = new Set(prev);
      if (next.has(id)) next.delete(id);
      else next.add(id);
      return next;
    });
  };

  const expandAll = () => {
    setExpandedIds(new Set(filteredQuestions.map(q => q.id)));
  };

  const collapseAll = () => {
    setExpandedIds(new Set());
  };

  const handleCopy = (text: string, id: string) => {
    navigator.clipboard.writeText(text);
    setCopiedId(id);
    setTimeout(() => setCopiedId(null), 2000);
  };

  const filteredQuestions = useMemo(() => {
    const q = searchQuery.toLowerCase().trim();
    return VISA_QUESTIONS.filter(item => {
      const matchesCategory = selectedCategory === 'ALL' || item.category === selectedCategory;
      const matchesQuery = !q || 
        item.question.toLowerCase().includes(q) ||
        item.sampleAnswer.toLowerCase().includes(q) ||
        item.officerIntent.toLowerCase().includes(q) ||
        item.shortSummary.toLowerCase().includes(q);
      return matchesCategory && matchesQuery;
    });
  }, [searchQuery, selectedCategory]);

  return (
    <div className="space-y-6">
      {/* Header Banner */}
      <div className="bg-white border border-slate-200 rounded-2xl p-6 shadow-xs">
        <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
          <div>
            <div className="flex items-center gap-2">
              <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-semibold bg-teal-50 text-teal-700 border border-teal-200">
                Official Consular Prep Manual
              </span>
              <span className="text-xs text-slate-500 font-medium">68 Questions with Model Answers</span>
            </div>
            <h2 className="text-2xl font-bold text-slate-900 mt-1">
              Complete U.S. Visa Interview Question Bank
            </h2>
            <p className="text-sm text-slate-600 mt-1 max-w-3xl leading-relaxed">
              Every question includes the consular officer's underlying psychological intent under INA Section 214(b), a recommended 30-second model answer, and fatal red flag traps to avoid.
            </p>
          </div>

          <div className="flex items-center gap-2">
            <button
              onClick={expandAll}
              className="px-3 py-1.5 text-xs font-semibold bg-slate-100 text-slate-700 hover:bg-slate-200 rounded-lg transition-colors"
            >
              Expand All
            </button>
            <button
              onClick={collapseAll}
              className="px-3 py-1.5 text-xs font-semibold bg-slate-100 text-slate-700 hover:bg-slate-200 rounded-lg transition-colors"
            >
              Collapse All
            </button>
            <button
              onClick={() => window.print()}
              className="flex items-center gap-1.5 px-3 py-1.5 text-xs font-semibold bg-[#0A2540] text-white hover:bg-slate-800 rounded-lg transition-colors"
            >
              <Printer className="w-3.5 h-3.5" />
              Print
            </button>
          </div>
        </div>

        {/* Search & Category Filter Bar */}
        <div className="mt-6 pt-5 border-t border-slate-100 space-y-3">
          <div className="relative">
            <Search className="w-4 h-4 text-slate-400 absolute left-3 top-3.5" />
            <input
              type="text"
              placeholder="Search across questions, sample answers, officer intent, or red flags..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              className="w-full pl-9 pr-4 py-2.5 bg-slate-50 text-sm text-slate-800 rounded-xl border border-slate-200 focus:outline-none focus:border-[#0A2540] focus:bg-white"
            />
          </div>

          <div className="flex gap-2 overflow-x-auto pb-1 no-scrollbar text-xs">
            {VISA_CATEGORIES.map(cat => (
              <button
                key={cat.id}
                onClick={() => setSelectedCategory(cat.id)}
                className={`whitespace-nowrap px-3 py-1.5 rounded-lg font-medium transition-all ${
                  selectedCategory === cat.id
                    ? 'bg-[#0A2540] text-white shadow-xs'
                    : 'bg-slate-100 text-slate-600 hover:bg-slate-200'
                }`}
              >
                {cat.name} ({cat.count})
              </button>
            ))}
          </div>
        </div>
      </div>

      {/* Results Count */}
      <div className="flex items-center justify-between px-2 text-xs font-semibold text-slate-500">
        <span>Showing {filteredQuestions.length} of {VISA_QUESTIONS.length} Questions</span>
        {searchQuery && <span>Filter applied: "{searchQuery}"</span>}
      </div>

      {/* Questions List */}
      <div className="space-y-4">
        {filteredQuestions.map((q, index) => {
          const isExpanded = expandedIds.has(q.id);

          return (
            <div
              key={q.id}
              className={`bg-white border rounded-2xl transition-all ${
                isExpanded ? 'border-teal-500/30 shadow-xs ring-1 ring-teal-500/10' : 'border-slate-200 hover:border-slate-300'
              }`}
            >
              {/* Question Header Card */}
              <div 
                className="p-5 cursor-pointer flex flex-col md:flex-row md:items-center justify-between gap-4 select-none"
                onClick={() => toggleExpand(q.id)}
              >
                <div className="flex items-start gap-3.5">
                  <span className="w-7 h-7 rounded-lg bg-slate-100 text-slate-700 flex items-center justify-center text-xs font-bold shrink-0 mt-0.5">
                    {index + 1}
                  </span>
                  <div>
                    <div className="flex items-center gap-2 mb-1">
                      <span className={`text-[11px] font-bold px-2 py-0.5 rounded ${
                        q.category === 'RED_FLAGS'
                          ? 'bg-rose-50 text-rose-700 border border-rose-200'
                          : q.category === 'TIES_214B'
                          ? 'bg-emerald-50 text-emerald-700 border border-emerald-200'
                          : 'bg-sky-50 text-sky-700 border border-sky-200'
                      }`}>
                        {q.categoryName}
                      </span>
                      <span className="text-xs text-slate-400">•</span>
                      <span className="text-xs text-slate-500">{q.shortSummary}</span>
                    </div>
                    <h3 className="font-bold text-base text-slate-900 leading-snug">
                      {q.question}
                    </h3>
                  </div>
                </div>

                <div className="flex items-center gap-3 shrink-0 self-end md:self-center">
                  <button
                    onClick={(e) => {
                      e.stopPropagation();
                      handleCopy(`${q.question}\n\nModel Answer:\n${q.sampleAnswer}\n\nOfficer Intent:\n${q.officerIntent}`, q.id);
                    }}
                    className="flex items-center gap-1 text-xs text-slate-500 hover:text-slate-700 p-1.5 rounded-lg hover:bg-slate-100"
                    title="Copy full question and answer"
                  >
                    {copiedId === q.id ? (
                      <>
                        <Check className="w-3.5 h-3.5 text-emerald-600" />
                        <span className="text-emerald-600 font-semibold text-xs">Copied</span>
                      </>
                    ) : (
                      <>
                        <Copy className="w-3.5 h-3.5" />
                        <span className="text-xs">Copy</span>
                      </>
                    )}
                  </button>

                  <div className="flex items-center gap-1 text-xs font-semibold text-teal-600">
                    {isExpanded ? 'Hide Details' : 'View Model Answer'}
                    {isExpanded ? <ChevronUp className="w-4 h-4" /> : <ChevronDown className="w-4 h-4" />}
                  </div>
                </div>
              </div>

              {/* Expanded Breakdown */}
              {isExpanded && (
                <div className="px-5 pb-5 pt-2 border-t border-slate-100 space-y-4 text-xs">
                  <div className="grid grid-cols-1 lg:grid-cols-2 gap-4">
                    {/* Model Answer */}
                    <div className="bg-emerald-50/70 border border-emerald-200 rounded-xl p-4 space-y-2">
                      <div className="flex items-center gap-2 text-emerald-800 font-bold text-xs">
                        <CheckCircle2 className="w-4 h-4 text-emerald-600" />
                        RECOMMENDED MODEL ANSWER (30 SECONDS)
                      </div>
                      <p className="text-slate-800 text-sm leading-relaxed italic bg-white/60 p-3 rounded-lg border border-emerald-100">
                        "{q.sampleAnswer}"
                      </p>
                      <div className="text-[11px] text-emerald-900 font-medium">
                        Tips: {q.tips.join(' • ')}
                      </div>
                    </div>

                    {/* Consular Officer Intent */}
                    <div className="bg-sky-50/70 border border-sky-200 rounded-xl p-4 space-y-2">
                      <div className="flex items-center gap-2 text-sky-800 font-bold text-xs">
                        <Info className="w-4 h-4 text-sky-600" />
                        WHAT THE CONSULAR OFFICER IS REALLY TESTING
                      </div>
                      <p className="text-slate-700 text-xs leading-relaxed bg-white/60 p-3 rounded-lg border border-sky-100">
                        {q.officerIntent}
                      </p>
                    </div>
                  </div>

                  {/* Red Flags & Fatal Pitfalls */}
                  <div className="bg-rose-50/70 border border-rose-200 rounded-xl p-4 space-y-2">
                    <div className="flex items-center gap-2 text-rose-800 font-bold text-xs">
                      <AlertTriangle className="w-4 h-4 text-rose-600" />
                      FATAL PITFALLS &amp; WHAT NOT TO SAY
                    </div>
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-2 text-xs text-rose-950">
                      {q.redFlags.map((flag, idx) => (
                        <div key={idx} className="flex items-start gap-2 bg-white/60 p-2.5 rounded-lg border border-rose-100">
                          <span className="text-rose-500 font-bold leading-tight">•</span>
                          <span className="leading-snug">{flag}</span>
                        </div>
                      ))}
                    </div>
                  </div>
                </div>
              )}
            </div>
          );
        })}
      </div>
    </div>
  );
};
