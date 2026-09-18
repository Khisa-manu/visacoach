import React, { useState, useEffect } from 'react';
import { 
  Volume2, VolumeX, RotateCcw, Shuffle, ChevronRight, ChevronLeft, 
  CheckCircle2, AlertTriangle, Info, Star, Award, Sparkles, Clock, Eye, EyeOff
} from 'lucide-react';
import { VisaQuestion } from '../data/visaQuestions';

interface PhoneMockDrillViewProps {
  questions: VisaQuestion[];
  bookmarkedIds: string[];
  onToggleBookmark: (id: string) => void;
  masteredIds: string[];
  onToggleMastered: (id: string) => void;
}

export const PhoneMockDrillView: React.FC<PhoneMockDrillViewProps> = ({
  questions,
  bookmarkedIds,
  onToggleBookmark,
  masteredIds,
  onToggleMastered
}) => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const [isRevealed, setIsRevealed] = useState(false);
  const [timerSeconds, setTimerSeconds] = useState(30);
  const [isTimerRunning, setIsTimerRunning] = useState(false);
  const [speaking, setSpeaking] = useState(false);

  const currentQ = questions[currentIndex] || questions[0];
  const isBookmarked = bookmarkedIds.includes(currentQ.id);
  const isMastered = masteredIds.includes(currentQ.id);

  // Timer countdown
  useEffect(() => {
    let interval: any = null;
    if (isTimerRunning && timerSeconds > 0) {
      interval = setInterval(() => {
        setTimerSeconds(s => s - 1);
      }, 1000);
    } else if (timerSeconds === 0 && isTimerRunning) {
      setIsTimerRunning(false);
      setIsRevealed(true);
    }
    return () => clearInterval(interval);
  }, [isTimerRunning, timerSeconds]);

  // Audio speech synthesis
  const handleSpeak = (text: string) => {
    if (!('speechSynthesis' in window)) return;
    if (speaking) {
      window.speechSynthesis.cancel();
      setSpeaking(false);
      return;
    }

    window.speechSynthesis.cancel();
    const utterance = new SpeechSynthesisUtterance(text);
    utterance.rate = 0.95;
    utterance.pitch = 1.0;
    utterance.onend = () => setSpeaking(false);
    utterance.onerror = () => setSpeaking(false);
    setSpeaking(true);
    window.speechSynthesis.speak(utterance);
  };

  const handleNext = () => {
    if (speaking && 'speechSynthesis' in window) {
      window.speechSynthesis.cancel();
      setSpeaking(false);
    }
    setIsRevealed(false);
    setTimerSeconds(30);
    setIsTimerRunning(false);
    setCurrentIndex(prev => (prev + 1) % questions.length);
  };

  const handlePrev = () => {
    if (speaking && 'speechSynthesis' in window) {
      window.speechSynthesis.cancel();
      setSpeaking(false);
    }
    setIsRevealed(false);
    setTimerSeconds(30);
    setIsTimerRunning(false);
    setCurrentIndex(prev => (prev - 1 + questions.length) % questions.length);
  };

  const handleShuffle = () => {
    if (speaking && 'speechSynthesis' in window) {
      window.speechSynthesis.cancel();
      setSpeaking(false);
    }
    setIsRevealed(false);
    setTimerSeconds(30);
    setIsTimerRunning(false);
    const randomIndex = Math.floor(Math.random() * questions.length);
    setCurrentIndex(randomIndex);
  };

  return (
    <div className="p-3 space-y-3">
      {/* Top Drill Controls & Progress */}
      <div className="bg-white rounded-2xl p-3 border border-slate-200 shadow-2xs flex items-center justify-between">
        <div className="flex items-center gap-2">
          <div className="w-6 h-6 rounded-lg bg-teal-50 text-teal-700 flex items-center justify-center font-bold text-xs border border-teal-200">
            {currentIndex + 1}
          </div>
          <div>
            <div className="text-[11px] font-bold text-slate-900">
              Mock Consular Drill
            </div>
            <div className="text-[10px] text-slate-500">
              Question {currentIndex + 1} of {questions.length} • {masteredIds.length} Mastered
            </div>
          </div>
        </div>

        <div className="flex items-center gap-1">
          <button
            onClick={handleShuffle}
            className="p-1.5 text-slate-500 hover:text-slate-800 rounded-lg hover:bg-slate-100 transition-colors"
            title="Random Question"
          >
            <Shuffle className="w-3.5 h-3.5" />
          </button>
          <button
            onClick={() => onToggleBookmark(currentQ.id)}
            className="p-1.5 rounded-lg hover:bg-slate-100 transition-colors"
            title={isBookmarked ? 'Remove Star' : 'Save Question'}
          >
            <Star className={`w-3.5 h-3.5 ${isBookmarked ? 'fill-amber-400 text-amber-500' : 'text-slate-400'}`} />
          </button>
        </div>
      </div>

      {/* Main Flashcard */}
      <div className="bg-white rounded-2xl border border-slate-200 shadow-xs overflow-hidden transition-all">
        {/* Card Header */}
        <div className="p-4 border-b border-slate-100 bg-slate-50/50 flex items-center justify-between">
          <span className={`text-[10px] font-bold px-2 py-0.5 rounded ${
            currentQ.category === 'RED_FLAGS'
              ? 'bg-rose-50 text-rose-700 border border-rose-200'
              : currentQ.category === 'TIES_214B'
              ? 'bg-emerald-50 text-emerald-700 border border-emerald-200'
              : 'bg-sky-50 text-sky-700 border border-sky-200'
          }`}>
            {currentQ.categoryName}
          </span>

          {/* 30-Second Spoken Timer */}
          <div className="flex items-center gap-1.5">
            <button
              onClick={() => setIsTimerRunning(!isTimerRunning)}
              className={`flex items-center gap-1 px-2 py-0.5 rounded-full text-[10px] font-bold transition-all ${
                isTimerRunning 
                  ? 'bg-rose-50 text-rose-700 border border-rose-200 animate-pulse' 
                  : 'bg-slate-100 text-slate-700 hover:bg-slate-200'
              }`}
            >
              <Clock className="w-3 h-3" />
              <span>{timerSeconds}s limit</span>
            </button>
          </div>
        </div>

        {/* Question Prompt */}
        <div className="p-4 space-y-3">
          <div className="text-[11px] font-bold text-teal-700 uppercase tracking-wider flex items-center gap-1">
            <Sparkles className="w-3 h-3" />
            <span>Consular Officer Asks:</span>
          </div>

          <h3 className="text-sm font-extrabold text-slate-900 leading-snug">
            "{currentQ.question}"
          </h3>

          <div className="bg-slate-50 rounded-xl p-2.5 border border-slate-100 text-[11px] text-slate-600 italic">
            Tip: Speak your response out loud in 20-30 seconds before revealing the model answer below.
          </div>
        </div>

        {/* Reveal Button */}
        {!isRevealed ? (
          <div className="p-3 bg-slate-50 border-t border-slate-100">
            <button
              onClick={() => setIsRevealed(true)}
              className="w-full py-2.5 bg-[#0A2540] text-white text-xs font-bold rounded-xl flex items-center justify-center gap-2 hover:bg-slate-800 transition-colors shadow-xs"
            >
              <Eye className="w-4 h-4 text-teal-400" />
              <span>Reveal Model Answer &amp; Intent</span>
            </button>
          </div>
        ) : (
          <div className="p-4 bg-slate-50/80 border-t border-slate-100 space-y-3 text-xs animate-in fade-in duration-200">
            {/* Model Answer */}
            <div className="bg-emerald-50 rounded-xl p-3 border border-emerald-200 space-y-1.5">
              <div className="flex items-center justify-between">
                <div className="flex items-center gap-1.5 text-emerald-900 font-bold text-[11px]">
                  <CheckCircle2 className="w-3.5 h-3.5 text-emerald-600" />
                  <span>30-SECOND MODEL ANSWER</span>
                </div>
                <button
                  onClick={() => handleSpeak(currentQ.sampleAnswer)}
                  className={`flex items-center gap-1 px-2 py-0.5 rounded-lg text-[10px] font-bold transition-all ${
                    speaking 
                      ? 'bg-emerald-600 text-white animate-pulse' 
                      : 'bg-white text-emerald-800 border border-emerald-200 hover:bg-emerald-100'
                  }`}
                  title="Listen to spoken model answer"
                >
                  {speaking ? <VolumeX className="w-3 h-3" /> : <Volume2 className="w-3 h-3" />}
                  <span>{speaking ? 'Stop' : 'Listen Cadence'}</span>
                </button>
              </div>
              <p className="text-slate-800 text-xs italic leading-relaxed bg-white/70 p-2 rounded-lg border border-emerald-100">
                "{currentQ.sampleAnswer}"
              </p>
            </div>

            {/* Officer Intent */}
            <div className="bg-sky-50 rounded-xl p-3 border border-sky-200 space-y-1">
              <div className="flex items-center gap-1 text-sky-900 font-bold text-[11px]">
                <Info className="w-3.5 h-3.5 text-sky-600" />
                <span>CONSULAR TEST UNDER INA 214(b)</span>
              </div>
              <p className="text-slate-700 text-[11px] leading-relaxed">
                {currentQ.officerIntent}
              </p>
            </div>

            {/* Red Flag Traps */}
            <div className="bg-rose-50 rounded-xl p-3 border border-rose-200 space-y-1">
              <div className="flex items-center gap-1 text-rose-900 font-bold text-[11px]">
                <AlertTriangle className="w-3.5 h-3.5 text-rose-600" />
                <span>FATAL RED FLAGS TO AVOID</span>
              </div>
              <ul className="text-rose-950 text-[11px] space-y-1 list-disc list-inside">
                {currentQ.redFlags.map((rf, i) => (
                  <li key={i} className="leading-snug">{rf}</li>
                ))}
              </ul>
            </div>

            {/* Mastered Toggle */}
            <div className="flex items-center justify-between pt-1">
              <button
                onClick={() => onToggleMastered(currentQ.id)}
                className={`flex items-center gap-1.5 px-3 py-1.5 rounded-xl text-xs font-bold transition-all ${
                  isMastered
                    ? 'bg-emerald-600 text-white'
                    : 'bg-white text-slate-700 border border-slate-200 hover:bg-slate-100'
                }`}
              >
                <Award className="w-3.5 h-3.5" />
                <span>{isMastered ? '✓ Mastered' : 'Mark as Mastered'}</span>
              </button>

              <button
                onClick={() => setIsRevealed(false)}
                className="text-[11px] text-slate-500 hover:text-slate-700 font-medium flex items-center gap-1"
              >
                <EyeOff className="w-3 h-3" />
                <span>Hide</span>
              </button>
            </div>
          </div>
        )}
      </div>

      {/* Navigation Buttons */}
      <div className="flex items-center justify-between gap-2 pt-1">
        <button
          onClick={handlePrev}
          className="flex-1 py-2 bg-white text-slate-700 border border-slate-200 hover:bg-slate-100 rounded-xl text-xs font-bold flex items-center justify-center gap-1 transition-colors"
        >
          <ChevronLeft className="w-3.5 h-3.5" />
          <span>Previous</span>
        </button>

        <button
          onClick={handleNext}
          className="flex-1 py-2 bg-[#0A2540] text-white hover:bg-slate-800 rounded-xl text-xs font-bold flex items-center justify-center gap-1 transition-colors shadow-2xs"
        >
          <span>Next</span>
          <ChevronRight className="w-3.5 h-3.5 text-teal-400" />
        </button>
      </div>
    </div>
  );
};
