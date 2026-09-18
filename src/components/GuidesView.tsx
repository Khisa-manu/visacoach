import React, { useState } from 'react';
import { BookOpen, CheckCircle2, Shield, AlertTriangle, Clock, ChevronRight } from 'lucide-react';
import { VISA_GUIDES, VisaGuideArticle } from '../data/visaQuestions';

export const GuidesView: React.FC = () => {
  const [selectedGuide, setSelectedGuide] = useState<VisaGuideArticle>(VISA_GUIDES[0]);

  return (
    <div className="max-w-6xl mx-auto space-y-8">
      {/* Header */}
      <div className="bg-white p-6 rounded-2xl border border-slate-200 shadow-xs">
        <div className="flex items-center gap-2 mb-2">
          <span className="px-2.5 py-0.5 bg-teal-50 text-teal-700 text-xs font-bold rounded-lg border border-teal-200">
            Consular Principles
          </span>
          <span className="text-xs text-slate-500">Legal &amp; Practical Embassy Guides</span>
        </div>
        <h2 className="text-2xl font-bold text-slate-900">
          U.S. Visa Interview Strategy &amp; Law Guides
        </h2>
        <p className="text-slate-600 text-sm mt-1 max-w-3xl leading-relaxed">
          Master the legal statutes (INA Section 214(b)), document preparation strategies, and psychological dynamics behind consular officer decisions.
        </p>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-12 gap-8">
        {/* Left List of Guides */}
        <div className="lg:col-span-4 space-y-3">
          {VISA_GUIDES.map(guide => {
            const isSelected = selectedGuide.id === guide.id;
            return (
              <div
                key={guide.id}
                onClick={() => setSelectedGuide(guide)}
                className={`p-4 rounded-xl border transition-all cursor-pointer ${
                  isSelected
                    ? 'bg-[#0A2540] text-white border-[#0A2540] shadow-md'
                    : 'bg-white text-slate-800 border-slate-200 hover:border-slate-300'
                }`}
              >
                <div className="flex items-center justify-between text-xs mb-1">
                  <span className={`font-bold uppercase text-[11px] ${isSelected ? 'text-teal-300' : 'text-teal-600'}`}>
                    {guide.category}
                  </span>
                  <span className={`text-[11px] ${isSelected ? 'text-slate-300' : 'text-slate-400'}`}>
                    {guide.readTime}
                  </span>
                </div>
                <h3 className={`font-bold text-sm leading-snug ${isSelected ? 'text-white' : 'text-slate-900'}`}>
                  {guide.title}
                </h3>
                <p className={`text-xs mt-1 line-clamp-2 ${isSelected ? 'text-slate-200' : 'text-slate-500'}`}>
                  {guide.summary}
                </p>
              </div>
            );
          })}
        </div>

        {/* Right Article Body */}
        <div className="lg:col-span-8 bg-white rounded-2xl border border-slate-200 p-8 shadow-xs space-y-6">
          <div className="border-b border-slate-100 pb-6">
            <div className="flex items-center gap-2 text-xs text-slate-500 mb-2">
              <span className="font-bold text-teal-600 uppercase tracking-wider">{selectedGuide.category}</span>
              <span>•</span>
              <span className="flex items-center gap-1">
                <Clock className="w-3.5 h-3.5 text-slate-400" /> {selectedGuide.readTime}
              </span>
            </div>
            <h1 className="text-2xl font-bold text-slate-900 leading-tight">
              {selectedGuide.title}
            </h1>
            <p className="text-slate-600 text-sm mt-2 italic leading-relaxed">
              {selectedGuide.summary}
            </p>
          </div>

          {/* Key Takeaways Callout */}
          <div className="bg-emerald-50/80 rounded-xl p-5 border border-emerald-200">
            <h4 className="text-xs font-bold text-emerald-800 tracking-wider uppercase mb-3 flex items-center gap-2">
              <CheckCircle2 className="w-4 h-4 text-emerald-600" /> Key Takeaways
            </h4>
            <ul className="space-y-2 text-xs text-emerald-950">
              {selectedGuide.keyPoints.map((point, i) => (
                <li key={i} className="flex items-start gap-2">
                  <span className="font-bold text-emerald-600">✓</span>
                  <span className="leading-relaxed">{point}</span>
                </li>
              ))}
            </ul>
          </div>

          {/* Article Paragraphs */}
          <div className="space-y-4 text-sm text-slate-700 leading-relaxed pt-2">
            {selectedGuide.content.map((paragraph, idx) => (
              <p key={idx} className="bg-slate-50/50 p-4 rounded-xl border border-slate-100">
                {paragraph}
              </p>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};
