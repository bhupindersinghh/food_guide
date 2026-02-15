'use client';

import { Recommendation } from '@/lib/api';
import Link from 'next/link';

interface RecommendationCardProps {
  recommendation: Recommendation;
  onInstagramClick?: () => void;
  onMapsClick?: () => void;
}

export default function RecommendationCard({
  recommendation,
  onInstagramClick,
  onMapsClick,
}: RecommendationCardProps) {
  const getCategoryEmoji = (category: string | null) => {
    const emojiMap: Record<string, string> = {
      BIRYANI: '🍛',
      MOMOS: '🥟',
      STREET_FOOD: '🍢',
      FINE_DINING: '🍽️',
      DESSERT: '🍰',
      BREAKFAST: '🍳',
      KEBABS: '🍖',
      CHINESE: '🥢',
      SOUTH_INDIAN: '🥞',
    };
    return category ? emojiMap[category] || '🍽️' : '🍽️';
  };

  return (
    <div className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-xl transition-shadow">
      {/* Thumbnail */}
      {recommendation.thumbnailUrl ? (
        <div className="aspect-video bg-gradient-to-br from-gray-200 to-gray-300">
          <img
            src={recommendation.thumbnailUrl}
            alt={recommendation.dishName}
            className="w-full h-full object-cover"
          />
        </div>
      ) : (
        <div className="aspect-video bg-gradient-to-br from-pink-100 to-orange-100 flex items-center justify-center">
          <span className="text-6xl">
            {getCategoryEmoji(recommendation.dishCategory)}
          </span>
        </div>
      )}

      {/* Content */}
      <div className="p-4">
        <h3 className="text-xl font-bold text-gray-900 mb-1">
          {getCategoryEmoji(recommendation.dishCategory)} {recommendation.dishName}
        </h3>
        <p className="text-lg text-gray-700 font-medium mb-1">
          {recommendation.restaurantName}
        </p>
        <p className="text-sm text-gray-500 mb-3">
          📍 {recommendation.area}
          {recommendation.city && `, ${recommendation.city}`}
        </p>

        {recommendation.creatorQuote && (
          <blockquote className="italic text-gray-600 border-l-4 border-pink-500 pl-3 mb-4 text-sm">
            "{recommendation.creatorQuote}"
          </blockquote>
        )}

        {/* Tags */}
        {recommendation.tags && recommendation.tags.length > 0 && (
          <div className="flex flex-wrap gap-2 mb-4">
            {recommendation.tags.slice(0, 3).map((tag) => (
              <span
                key={tag}
                className="bg-gray-100 text-gray-600 text-xs px-2 py-1 rounded-full"
              >
                {tag}
              </span>
            ))}
          </div>
        )}

        {/* Buttons */}
        <div className="flex gap-2">
          {recommendation.instagramUrl && (
            <a
              href={recommendation.instagramUrl}
              target="_blank"
              rel="noopener noreferrer"
              onClick={onInstagramClick}
              className="flex-1 text-center py-2 px-4 bg-instagram-gradient text-white rounded-lg font-medium hover:opacity-90 transition"
            >
              ▶️ Watch Reel
            </a>
          )}
          {recommendation.googleMapsUrl && (
            <a
              href={recommendation.googleMapsUrl}
              target="_blank"
              rel="noopener noreferrer"
              onClick={onMapsClick}
              className="flex-1 text-center py-2 px-4 border-2 border-gray-300 text-gray-700 rounded-lg font-medium hover:border-pink-500 hover:text-pink-500 transition"
            >
              📍 Location
            </a>
          )}
        </div>

        {/* Metadata */}
        <div className="mt-3 flex items-center justify-between text-xs text-gray-400">
          <span>👁️ {recommendation.viewCount} views</span>
          <span>{new Date(recommendation.publishedAt).toLocaleDateString()}</span>
        </div>
      </div>
    </div>
  );
}
